package bot.procyon.util

import dev.kord.core.behavior.channel.MessageChannelBehavior
import dev.kord.core.behavior.channel.createEmbed

suspend fun displayExceptionEmbed(exception: Exception?, channel: MessageChannelBehavior) {
    if (exception == null) return

    channel.createEmbed {
        title = exception::class.simpleName.toString()
        description = exception.message.toString()
        color = EmbedColor.ERROR.value
    }
}

