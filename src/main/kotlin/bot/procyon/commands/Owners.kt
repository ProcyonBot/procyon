package bot.procyon.commands

import bot.procyon.util.EmbedColor
import bot.procyon.util.getUserOrNull
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message
import dev.kord.rest.builder.message.create.embed

class Owners : Command() {
    override val name = "owners"
    override val description = "View the bot owners."
    override val hasArgs = false

    override suspend fun execute(message: Message, args: List<String?>) {

        message.reply {
            embed {
                title = "Bot owners"
                color = EmbedColor.DEFAULT.value

                config.superusers.forEach {
                    val user = getUserOrNull(it.toString(), kord)!!
                    field {
                        name = user.tag
                        value = user.id.toString()
                        inline = true
                    }
                }
            }
        }
    }
}