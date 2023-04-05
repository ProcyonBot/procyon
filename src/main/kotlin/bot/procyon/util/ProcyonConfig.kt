package bot.procyon.util

import dev.kord.common.entity.Snowflake
import kotlinx.serialization.Serializable

@Serializable
data class ProcyonConfig(
    val token: String = "NO TOKEN",
    val prefix: String = "!",
    val superusers: List<Snowflake?> = emptyList()
)