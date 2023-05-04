package bot.procyon.commands

import bot.procyon.util.EmbedColor
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message
import dev.kord.rest.builder.message.create.embed

const val RESULTS_PER_PAGE = 9
class Help : Command() {
    override val name = "help"
    override val description = "View available commands and information about them."
    override val usage = "[command]"

    override suspend fun execute(message: Message, args: List<String?>) {
        // todo: pagination of some kind
        var resultsThisPage = 0
        if (args.firstOrNull().isNullOrBlank()) {
            message.reply {
                embed {
                    title = "Commands"
                    color = EmbedColor.INFO.value

                    commands.forEach {
                        if (resultsThisPage < RESULTS_PER_PAGE) {
                            field {
                                name = it.name
                                value = it.description
                                inline = true
                            }
                            resultsThisPage++
                        }
                    }
                }
            }
        } else {
            val command = try {
                commands.first { it.name == args[0] || it.aliases.contains(args[0]) }
            } catch (_: NoSuchElementException) {
                message.reply {
                    embed {
                        title = "No such command."
                        description = "Command ${args[0]!!.take(30)} does not exist."
                        color = EmbedColor.ERROR.value
                    }
                }
                return@execute
            }


            message.reply {
                embed {
                    title = command.name
                    description = command.description
                    color = EmbedColor.INFO.value

                    if (command.usage.isNotBlank()) {
                        field {
                            name = "Usage"
                            value = "${command.name} ${command.usage}" // ?? Else we get bot.procyon.commands.EightBall@27b45ea.name
                        }
                        footer {
                            text = "<arg> is required, [arg] is optional."
                        }
                    }

                    if (command.aliases.isNotEmpty()) {
                        field {
                            name = "Aliases"
                            value = command.aliases.joinToString(", ")
                        }
                    }
                }
            }
        }
    }
}