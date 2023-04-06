package bot.procyon.util

import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.behavior.UserBehavior
import dev.kord.core.entity.Member
import dev.kord.core.entity.User

fun Member.displayAvatar() = memberAvatar ?: avatar ?: defaultAvatar
fun User.displayAvatar() = avatar ?: defaultAvatar

suspend fun getUserOrNull(string: String?, kord: Kord): User? {
    if (string.isNullOrBlank()) return null

    var parsedId: Snowflake = Snowflake(0)

    try {
        parsedId = Snowflake(string)
    } catch (e: Exception) {
        if (string.startsWith("<@")) {
            parsedId = Snowflake(
                string.replace("<", "")
                    .replace(">", "")
                    .replace("@", "")
                    .replace("!", "")
            )
        }
    }
    return UserBehavior(parsedId, kord).asUserOrNull()
}