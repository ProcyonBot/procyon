package bot.procyon.util

import dev.kord.common.entity.optional.optional
import dev.kord.core.Kord
import dev.kord.core.cache.data.EmbedData
import dev.kord.core.entity.Embed
import java.lang.Exception

fun exceptionEmbedDisplay(exception: Exception, kord: Kord): Embed {
    return Embed(data = EmbedData(
        title = exception::class.simpleName.toString().optional(),
        description = exception.message.toString().optional()
    ), kord)
}

