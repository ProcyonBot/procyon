package bot.procyon.commands

import dev.kord.core.behavior.channel.createMessage
import dev.kord.core.entity.Message
import dev.kord.rest.builder.message.create.embed

class CoinFlip : Command(
    name = "coinflip",
    aliases = listOf("coin", "flip"),
) {
    private val chances = listOf("Heads", "Tails")

    override suspend fun execute(message: Message, args: List<String?>) {
        message.channel.createMessage {
            embed {
                title = "Flipped a coin and got ${chances.random()}!"
            }
        }
    }
}
