package bot.procyon.commands

import bot.procyon.util.displayAvatar
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message
import dev.kord.rest.builder.message.create.embed

class Avatar : Command() {
    override val name = "avatar"
    override val aliases = listOf("a")
    override val description = "View your own, or someone else's, avatar."
    override val usage = "[user]"

    override suspend fun execute(message: Message, args: List<String?>) {
        val member = message.getAuthorAsMemberOrNull() ?: message.author!!
        val avatar = member.displayAvatar()

        message.reply {
            embed {
                title = "Avatar of ${member.username}"
                image = avatar.url
            }
        }
    }
}