package bot.procyon.commands

import bot.procyon.util.ProcyonConfig
import dev.kord.core.Kord
import dev.kord.core.entity.Message
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.komapper.r2dbc.R2dbcDatabase
import kotlin.time.Duration

sealed class Command : KoinComponent {
    abstract val name: String
    open val description: String = "No description provided."
    open val usage: String = ""
    open val aliases: List<String> = emptyList()
    open val enabled: Boolean = true
    open val hasArgs: Boolean = false
    open val cooldown: Duration = Duration.ZERO

    protected val kord: Kord by inject()
    protected val config: ProcyonConfig by inject()
    protected val commands: List<Command> by inject()
    protected val database: R2dbcDatabase by inject()

    // series of checks
    open suspend fun check(message: Message): Boolean = true

    abstract suspend fun execute(
        message: Message,
        args: List<String?> = emptyList()
    ) // to be overridden with `override fun`
}
