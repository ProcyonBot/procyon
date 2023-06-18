package bot.procyon.commands

import bot.procyon.util.*
import dev.kord.core.entity.Message
import dev.kord.core.entity.User

class Balance : Command() {
    override val name = "balance"
    override val aliases = listOf("bal", "xp", "exp")
    override val description = "Check your balance and XP."

    override suspend fun execute(message: Message, args: List<String?>) {
        val person: User = if (args.any() && !args[0].isNullOrBlank()) {
            getUser(args[0]!!, kord)
        } else {
            message.author!!
        }
        val user = database.getOrCreateUser(person.id)

        message.reply(embed {
            title = "${person.tag}'s balance"
            color = EmbedColor.INFO.value
            field {
                name = "Balance"
                value = user.balance.toString()
                inline = true
            }
            field {
                name = "EXP"
                value = user.exp.toString()
                inline = true
            }
        })
    }


}
