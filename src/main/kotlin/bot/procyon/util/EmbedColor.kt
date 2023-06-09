package bot.procyon.util

import dev.kord.common.Color

enum class EmbedColor(val value: Color) {
    SUCCESS(Color(48, 192, 48)),
    WARN(Color(226, 226, 12)),
    ERROR(Color(192, 48, 48)),
    INFO(Color(48, 48, 192)),
    DEFAULT(Color(160, 160, 160)),
    PINK(Color(226, 160, 226)) // god damn it's PINK!!

}
