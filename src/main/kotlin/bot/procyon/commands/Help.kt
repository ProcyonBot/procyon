package bot.procyon.commands

import bot.procyon.util.EmbedColor
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message
import dev.kord.rest.builder.message.create.embed

class Help : Command() {
    override val name = "help"
    override val description = "View available commands and information about them."

    override suspend fun execute(message: Message, args: List<String?>) {
        // todo: implement command specific help
        // todo: pagination of some kind


        message.reply {
            embed {
                title = "Commands"
                color = EmbedColor.DEFAULT.value

                commands.forEach {
                    field {
                        name = it.name
                        value = it.description
                        inline = true
                    }
                }
            }
        }
    }
}