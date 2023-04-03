package bot.procyon.commands

import bot.procyon.util.displayAvatar
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message
import dev.kord.rest.builder.message.create.embed

class Avatar : Command("avatar") {
    override suspend fun execute(message: Message, args: List<String?>) {
        val avatar = (message.getAuthorAsMemberOrNull() ?: message.author!!).displayAvatar()

        message.reply {
            embed {
                image = avatar.url
            }
        }
    }
}