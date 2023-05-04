package bot.procyon.commands

import bot.procyon.util.EmbedColor
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message
import dev.kord.rest.builder.message.create.embed

class OS : Command() {
    override val name = "os"
    override val hasArgs = false
    override val description = "Bot system information."

    override suspend fun check(message: Message): Boolean {
        return config.superusers.contains(message.author?.id)
    }

    override suspend fun execute(message: Message, args: List<String?>) {
        message.reply {
            embed {
                title = "System information"
                color = EmbedColor.DEFAULT.value

                field {
                    name = "OS"
                    value = System.getProperty("os.name")
                    inline = true
                }

                field {
                    name = "Kotlin version"
                    value = "v" + KotlinVersion.CURRENT.toString()
                    inline = true
                }

                field {
                    name = "JDK version"
                    value = "v" + System.getProperty("java.version")
                    inline = true
                }

            }
        }
    }
}