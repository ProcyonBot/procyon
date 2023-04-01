package bot.procyon.commands

class Ping {
    val name = "ping"
    fun execute() {

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