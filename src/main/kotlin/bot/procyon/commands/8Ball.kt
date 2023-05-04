package bot.procyon.commands

import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.entity.Message

class EightBall : Command() {
    override val name = "8ball"
    override val hasArgs = true
    override val description = "Ask the Magic 8 Ball anything!"
    override val usage = "<question>"

    override suspend fun execute(message: Message, args: List<String?>) {
        message.channel.createEmbed {
            title = "$EIGHT_BALL Magic 8 Ball $EIGHT_BALL"
            field {
                name = "Question"
                value = args.joinToString(" ")
            }
            field {
                name = "Answer"
                value = answers.random()
            }
        }
    }

    private companion object {
        private const val EIGHT_BALL = "\uD83C\uDFB1"
        private val answers =
            listOf("It is certain", "It is decidedly so", "Without a doubt",
                "Yes, definitely", "You may rely on it", "As I see it, yes",
                "Most likely", "Outlook good", "Yes", "Signs point to yes",
                "Reply hazy, try again", "Ask again later", "Better not tell you now",
                "Cannot predict now", "Concentrate and ask again", "Don't count on it",
                "My reply is no", "My sources say no", "Outlook not so good", "Very doubtful")
    }
}