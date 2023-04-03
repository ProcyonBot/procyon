package bot.procyon.util

import dev.kord.core.behavior.channel.MessageChannelBehavior
import dev.kord.core.behavior.channel.createEmbed

suspend fun MessageChannelBehavior.displayExceptionEmbed(exception: Exception) = createEmbed {
    title = exception::class.simpleName.toString()
    description = exception.message.toString()
    color = EmbedColor.ERROR.value
}

