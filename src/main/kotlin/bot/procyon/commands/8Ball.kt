package bot.procyon.commands

import dev.kord.core.behavior.channel.createMessage
import dev.kord.core.entity.Message
import dev.kord.rest.builder.message.create.embed

class EightBall : Command("8ball") {
    private val answers =
        listOf("It is certain", "It is decidedly so", "Without a doubt",
            "Yes, definitely", "You may rely on it", "As I see it, yes",
            "Most likely", "Outlook good", "Yes", "Signs point to yes",
            "Reply hazy, try again", "Ask again later", "Better not tell you now",
            "Cannot predict now", "Concentrate and ask again", "Don't count on it",
            "My reply is no", "My sources say no", "Outlook not so good", "Very doubtful")
    override suspend fun execute(message: Message, args: List<String?>) {
        message.channel.createMessage {
            embed {
                title = "\uD83C\uDFB1 Magic 8 Ball \uD83C\uDFB1"
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
    }
}