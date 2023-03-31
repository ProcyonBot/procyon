package bot.procyon

import dev.kord.core.Kord
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import kotlinx.coroutines.delay

class Procyon {
    suspend fun main() {
        val kord = Kord("") // TODO: How to load this from a config file?
        val pingPong = ReactionEmoji.Unicode("\uD83C\uDFD3")

        kord.on<MessageCreateEvent> {
            if (message.content != "!ping") return@on

            val response = message.channel.createMessage("Pong!")
            response.addReaction(pingPong)

            delay(5000)
            message.delete()
            response.delete()
        }

        kord.login {
            @OptIn(PrivilegedIntent::class)
            intents += Intent.MessageContent
        }
    }
}