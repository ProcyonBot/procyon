package bot.procyon.commands

import dev.kord.common.Color
import dev.kord.core.behavior.channel.createMessage
import dev.kord.core.entity.Message
import dev.kord.rest.builder.message.create.embed
import kotlin.math.abs

class Ping : Command("ping") {
    override suspend fun execute(message: Message, args: List<String?>) {
        val latency = System.currentTimeMillis() - message.timestamp.toEpochMilliseconds()
        val pingEmbedColor = calculateColor(latency.toFloat())

        message.channel.createMessage {
            embed {
                title = "Ping!"
                description = "Took $latency ms."
                color = pingEmbedColor
            }
        }
    }
}

fun calculateColor(ratio: Float): Color {
    val low = 48
    val high = 192

    val diff = high - low
    val redStep = diff / ratio
    val greenStep = (-diff) / ratio

    val redFinal = abs(high - (redStep * diff))
    val greenFinal = abs(low - (greenStep * diff))

    return Color(redFinal.toInt(), greenFinal.toInt(), 48)
}