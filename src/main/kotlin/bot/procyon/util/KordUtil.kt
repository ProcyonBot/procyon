package bot.procyon.util

import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message
import dev.kord.rest.builder.message.EmbedBuilder

suspend fun Message.reply(content: String) = reply { this.content = content }
suspend fun Message.reply(embed: EmbedBuilder) = reply { embeds.add(embed) }

inline fun embed(block: EmbedBuilder.() -> Unit) = EmbedBuilder().also(block)