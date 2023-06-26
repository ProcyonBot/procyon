package bot.procyon.commands

import bot.procyon.models.user
import bot.procyon.util.EmbedColor
import bot.procyon.util.embed
import bot.procyon.util.getOrCreateUser
import bot.procyon.util.reply
import dev.kord.core.entity.Message
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import java.text.NumberFormat
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Guess : Command() {
    override val name = "guess"
    override val description = "Guess 1-100 for some simple, quick money."
    override val hasArgs = true
    override val usage = "number"

    override suspend fun execute(message: Message, args: List<String?>) { // TODO: COOLDOWNS good GOD
        val guess = args[0]?.toBigIntegerOrNull()?.toFloat()?.let { max(min(it, 100F), 0F) } ?: throw Exception()
        val winning = (0..100).random().toFloat()
        val userWon = (guess == winning)

        val earnings = if (userWon) {
            1000.0
        } else {
            (50 / abs(winning - guess) * 4).toDouble()
        }

        val dbUser = database.getOrCreateUser(message.author!!.id)
        val updatedUserQuery = QueryDsl.update(Meta.user).single(
            dbUser.copy(balance = dbUser.balance + earnings)
        )
        database.runQuery(updatedUserQuery)

        message.reply(embed {
            title = "You won ${NumberFormat.getCurrencyInstance().format(earnings)}!"
            field {
                name = "Your guess"
                value = guess.toInt().toString()
                inline = true
            }
            field {
                name = "Winning number"
                value = winning.toInt().toString()
            }
            color = if (userWon) {
                EmbedColor.PINK
            } else {
                EmbedColor.SUCCESS
            }.value
        })
    }
}