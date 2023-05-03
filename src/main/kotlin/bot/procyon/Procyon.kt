package bot.procyon

import bot.procyon.commands.Command
import bot.procyon.di.botModule
import bot.procyon.util.*
import dev.kord.core.Kord
import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.kordLogger
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.extension.coroutinesEngine
import org.koin.environmentProperties

@OptIn(KoinExperimentalAPI::class)
private fun main() {
    val application = startKoin {
        environmentProperties()
        modules(botModule)
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

    suspend fun run() = runBlocking {
        val commands = commandsInjected

        kord.on<ReadyEvent> {
            kordLogger.info("Logged in as ${kord.getSelf().username}!")
        }

        kord.on<MessageCreateEvent> {
            launch { // Should allow for multiple commands to be executed at once
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
                    !cmd.check() -> ProcyonChecksException(cmdStr)
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