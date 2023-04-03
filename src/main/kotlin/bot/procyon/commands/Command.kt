package bot.procyon.commands

import dev.kord.core.Kord
import dev.kord.core.entity.Message
import kotlin.properties.Delegates

sealed class Command {
    abstract val name: String
    open val description: String = "No description provided."
    open val aliases: List<String> = emptyList()
    open val enabled: Boolean = true
    open val hasArgs: Boolean = false

    private val kord: Kord by Delegates.notNull()

    // series of checks
    open suspend fun check(): Boolean = true

    abstract suspend fun execute(
        message: Message,
        args: List<String?> = emptyList()
    ) // to be overridden with `override fun`
}
