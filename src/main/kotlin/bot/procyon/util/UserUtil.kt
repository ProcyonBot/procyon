package bot.procyon.util

import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.behavior.UserBehavior
import dev.kord.core.entity.Member
import dev.kord.core.entity.User
import dev.kord.core.exception.EntityNotFoundException

fun Member.displayAvatar() = memberAvatar ?: avatar ?: defaultAvatar
fun User.displayAvatar() = avatar ?: defaultAvatar

suspend fun getUserOrNull(string: String?, kord: Kord): User? {
    if (string.isNullOrBlank()) return null

    var parsedId = Snowflake(0)

    try {
        parsedId = Snowflake(string)
    } catch (e: Exception) {
        if (string.startsWith("<@")) {
            var parsingString = string.replace("<", "")
                .replace(">", "")
                .replace("@", "")
                .replace("!", "")
            if (parsingString.isEmpty()) parsingString = "0"
            parsedId = Snowflake(parsingString)
        }
    }
    return UserBehavior(parsedId, kord).asUserOrNull()
}

suspend fun getUser(string: String, kord: Kord): User {
    if (string.isBlank()) throw EntityNotFoundException("Blank user provided")

    var parsedId = Snowflake(0)

    try {
        parsedId = Snowflake(string)
    } catch (e: Exception) {
        if (string.startsWith("<@")) {
            var parsingString = string.replace("<", "")
                .replace(">", "")
                .replace("@", "")
                .replace("!", "")
            if (parsingString.isEmpty()) parsingString = "0"
            parsedId = Snowflake(parsingString)
        }
    }
    return UserBehavior(parsedId, kord).asUser()
}