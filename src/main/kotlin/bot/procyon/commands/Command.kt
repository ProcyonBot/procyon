package bot.procyon.commands

import dev.kord.core.Kord
import dev.kord.core.entity.Message
import kotlin.properties.Delegates

sealed class Command(
    val name: String,
    val description: String = "No description provided.",
    val aliases: List<String> = emptyList(),
    val enabled: Boolean = true,
    val hasArgs: Boolean = true
) {
    private val kord: Kord by Delegates.notNull()

    // series of checks
    open suspend fun check(): Boolean = true

    abstract suspend fun execute(
        message: Message,
        args: List<String?> = emptyList()
    ) // to be overridden with `override fun`
}
