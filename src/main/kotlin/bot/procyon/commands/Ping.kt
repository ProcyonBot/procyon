package bot.procyon.commands

import dev.kord.common.Color
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.entity.Message
import kotlin.math.abs

class Ping : Command() {
    override val name = "ping"
    override val description = "Ping!"
    override val hasArgs = false

    override suspend fun execute(message: Message, args: List<String?>) {
        val latency = System.currentTimeMillis() - message.timestamp.toEpochMilliseconds()
        val pingEmbedColor = calculateColor(latency.toFloat())

        message.channel.createEmbed {
            title = "Ping!"
            description = "Took $latency ms."
            color = pingEmbedColor
        }
    }
}

fun calculateColor(ratio: Float): Color {
    val low = 48
    val high = 192

    val diff = high - low
    val redStep = diff / ratio
    val greenStep = (-diff) / ratio

    var redFinal = abs(high - (redStep * diff))
    var greenFinal = abs(low - (greenStep * diff))

    if (greenFinal > 255) greenFinal = 255f
    if (redFinal   > 255) redFinal   = 255f

    return Color(redFinal.toInt(), greenFinal.toInt(), 48)
}