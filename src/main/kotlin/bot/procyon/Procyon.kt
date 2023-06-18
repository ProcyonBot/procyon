package bot.procyon

import bot.procyon.commands.Command
import bot.procyon.di.botModule
import bot.procyon.models.guild
import bot.procyon.models.user
import bot.procyon.util.*
import dev.kord.core.Kord
import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.kordLogger
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.Option
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.extension.coroutinesEngine
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.environmentProperties
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase

@OptIn(KoinExperimentalAPI::class)
private suspend fun main() {
    val dbLocation = "./procyon.db"

    val options = ConnectionFactoryOptions.builder()
        .option(ConnectionFactoryOptions.DRIVER, "h2")
        .option(ConnectionFactoryOptions.PROTOCOL, "file")
        .option(ConnectionFactoryOptions.DATABASE, dbLocation)
        .option(Option.valueOf("DB_CLOSE_DELAY"), "-1")
        .build()

    val db = R2dbcDatabase(options)

    db.withTransaction {
        db.runQuery(QueryDsl.create(Meta.guild, Meta.user))
    }

    val databaseModule = module {
        fun provideDatabase(): R2dbcDatabase {
            return db
        }

        singleOf(::provideDatabase)
    }

    val application = startKoin {
        environmentProperties()
        modules(botModule)
        modules(databaseModule)
        coroutinesEngine()
    }

    runBlocking {
        Procyon().run()
    }
}

private class Procyon : KoinComponent {

    private val kord: Kord by inject()
    private val config: ProcyonConfig by inject()
    private val commandsInjected: List<Command> by inject()
    private val database: R2dbcDatabase by inject()

    suspend fun run() = runBlocking {
        val commands = commandsInjected

        kord.on<ReadyEvent> {
            kordLogger.info("Logged in as ${kord.getSelf().username}!")
        }

        kord.on<MessageCreateEvent> {
            launch { // Should allow for multiple commands to be executed at once

                if (message.author == null) {
                    return@launch
                }
                // Update user EXP.. TODO: is this okay?
                val dbUser = database.getOrCreateUser(message.author!!.id)
                val updatedUserQuery = QueryDsl.update(Meta.user).single(
                    dbUser.copy(exp = dbUser.exp + 1)
                )
                database.runQuery(updatedUserQuery)

                if (!message.content.startsWith(config.prefix)) return@launch

                // NOTE: Converting to lowercase effects args, which might be bad if you want to use case-sensitive args
                val fullString = message.content.drop(config.prefix.length).lowercase()

                val cmdStr = fullString.substringBefore(" ")
                val args   = fullString.substringAfter(" ", "")
                    .split(" ")
                    .filter(String::isNotEmpty)

                val cmd = commands.find {
                    it.name == cmdStr || it.aliases.contains(cmdStr)
                } ?: return@launch

                val commandFailException = when {
                    !cmd.enabled -> ProcyonDisabledException(cmdStr)
                    !cmd.check(message) -> ProcyonChecksException(cmdStr)
                    cmd.hasArgs && args.isEmpty() -> ProcyonNeedsArgsException(cmdStr)
                    else -> null
                }

                if (commandFailException != null) {
                    message.channel.displayExceptionEmbed(commandFailException)
                    return@launch
                }

                try {
                    cmd.execute(message, args)
                    kordLogger.info { "Executed command ${cmd.name} as $cmdStr." }
                } catch (e: Exception) {
                    kordLogger.error(e) { "Error executing command ${cmd.name}." }
                    message.channel.displayExceptionEmbed(e)
                }
            }
        }

        kord.login {
            @OptIn(PrivilegedIntent::class)
            intents += Intent.MessageContent
        }
    }
}