package bot.procyon.commands

import bot.procyon.util.EmbedColor
import bot.procyon.util.ProcyonCommandException
import bot.procyon.util.displayAvatar
import bot.procyon.util.getUserOrNull
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Member
import dev.kord.core.entity.Message
import dev.kord.core.entity.User
import dev.kord.rest.builder.message.create.embed

class UserInfo : Command() {
    override val name = "userinfo"
    override val description = "View information about a user."

    override suspend fun execute(message: Message, args: List<String?>) {
        var person: Member = message.getAuthorAsMemberOrThrow()
        var maybeUser: User? = person
        if (args.any()) {
            maybeUser = getUserOrNull(args[0], kord)
        }
        try {
            if (maybeUser != null) {
                person = maybeUser.asMember(message.getGuild().id)
            }
        } catch (e: Exception) {
            throw ProcyonCommandException(name, "User ${person.tag} is not in guild ${message.getGuild().name}")
        }

        val isSuperuser = config.superusers.contains(person.id)

        message.reply {
            embed {

                title = person.tag
                color = person.accentColor ?: EmbedColor.SUCCESS.value
                thumbnail {
                    url = person.displayAvatar().url
                }
                field {
                    name = "Joined guild at"
                    value = person.joinedAt.toString()
                }
                field {
                    name = "Flags"
                    value = person.publicFlags?.flags?.joinToString {
                        it.name
                    } ?: "None"
                }
                if (isSuperuser) field {
                    name = "Is bot superuser"
                } // TODO: More
            }
        }
    }
}