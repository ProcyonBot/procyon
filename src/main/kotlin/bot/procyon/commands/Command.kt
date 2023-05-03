package bot.procyon.commands

import bot.procyon.util.ProcyonConfig
import dev.kord.core.Kord
import dev.kord.core.entity.Message
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

sealed class Command : KoinComponent {
    abstract val name: String
    open val description: String = "No description provided."
    open val usage: String = "<args>"
    open val aliases: List<String> = emptyList()
    open val enabled: Boolean = true
    open val hasArgs: Boolean = false

    protected val kord: Kord by inject()
    protected val config: ProcyonConfig by inject()
    protected val commands: List<Command> by inject()

    // series of checks
    open suspend fun check(): Boolean = true

    abstract suspend fun execute(
        message: Message,
        args: List<String?> = emptyList()
    ) // to be overridden with `override fun`
}
