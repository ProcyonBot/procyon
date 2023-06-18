package bot.procyon.commands

import bot.procyon.util.*
import dev.kord.core.entity.Message
import dev.kord.core.entity.User
import java.text.NumberFormat
import kotlin.math.floor
import kotlin.math.sqrt

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
        val level = floor(sqrt(user.exp.toDouble()) / 3).toInt()

        message.reply(embed {
            title = "${person.globalName}'s balance"
            color = EmbedColor.INFO.value
            field {
                name = "Balance"
                value = NumberFormat.getCurrencyInstance().format(user.balance)
                inline = true
            }
            field {
                name = "EXP"
                value = "${user.exp.toString()} (level **$level**)"
                inline = true
            }
        })
    }


}
