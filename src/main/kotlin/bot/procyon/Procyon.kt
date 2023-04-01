package bot.procyon

import bot.procyon.commands.Command
import bot.procyon.di.botModule
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

private const val PREFIX = "!"

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

    suspend fun run() = runBlocking {
        val commands = Command::class.sealedSubclasses.map {
            it::constructors.get().single().call(kord)
        }

        kord.on<ReadyEvent> {
            kordLogger.info("Logged in as ${kord.getSelf().username}!")
        }

        kord.on<MessageCreateEvent> {
            launch { // Should allow for multiple commands to be executed at once
                if (!message.content.startsWith(PREFIX)) return@launch

                val cmdStr = message.content.drop(1).substringBefore(" ").lowercase()

                val cmd = commands.find {
                    it.name == cmdStr || it.aliases.contains(cmdStr)
                } ?: return@launch

                if (!cmd.check() || !cmd.enabled) return@launch

                try {
                    cmd.execute(message, emptyList())

                    // badabinga
                    kordLogger.info { "Executed command ${cmd.name} as $cmdStr." }
                } catch (e: Exception) {
                    kordLogger.error(e) { "Error executing command ${cmd.name}." }
                }
            }
        }

        kord.login {
            @OptIn(PrivilegedIntent::class)
            intents += Intent.MessageContent
        }
    }
}