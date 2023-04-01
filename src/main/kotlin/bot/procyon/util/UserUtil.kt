package bot.procyon.util

import dev.kord.core.entity.Member
import dev.kord.core.entity.User

fun Member.displayAvatar() = memberAvatar ?: avatar ?: defaultAvatar
fun User.displayAvatar() = avatar ?: defaultAvatar