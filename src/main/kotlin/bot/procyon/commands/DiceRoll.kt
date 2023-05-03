package bot.procyon.commands

import bot.procyon.util.getResource
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message
import dev.kord.rest.builder.message.create.embed
import io.ktor.client.request.forms.*
import io.ktor.utils.io.jvm.javaio.*

fun diceAliases(sides: Int): List<String> = listOf(sides.toString(), "d${sides.toString()}")

class DiceRoll : Command() {
    override val name = "diceroll"
    override val aliases = listOf("roll", "dice")
    override val description = "Roll a dice! Currently supports: d6, d8."

    override suspend fun execute(message: Message, args: List<String?>) {
        val type = when (args.firstOrNull()) {
                in diceAliases(6) -> "d6" //TODO: Other types of dice!
                in diceAliases(8) -> "d8"
                //in diceAliases(10) -> "d10"
                //in diceAliases(12) -> "d12"
                //in diceAliases(20) -> "d20"
                else -> "d6"
            }
        val max = type.drop(1).toInt()
        val roll = (1..max).random()

        message.reply {
            files += addFile("dice.png", ChannelProvider { getResource("/dice/$type/${roll}.png")!!.toByteReadChannel() })
            embed {
                title = "Rolled a $type and got $roll!"
                image = "attachment://dice.png"
            }

        }
    }
}