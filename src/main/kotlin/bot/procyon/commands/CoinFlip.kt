package bot.procyon.commands

import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.entity.Message

class CoinFlip : Command() {
    override val name = "coinflip"
    override val aliases = listOf("coin", "flip")
    override val description = "Flip a coin."
    override val hasArgs = false

    override suspend fun execute(message: Message, args: List<String?>) {
        message.channel.createEmbed {
            title = "Flipped a coin and got ${chances.random()}!"
        }
    }

    private companion object {
        private val chances = listOf("Heads", "Tails")
    }
}
