package bot.procyon.util

import dev.kord.core.entity.User
import io.github.cdimascio.dotenv.dotenv

data class ProcyonConfig(
    val token: String = "NO TOKEN",
    val prefix: String = "!",
    val owners: List<User?> = emptyList()
)

val dotenv = dotenv()

// OWNERS in the env file is meant to be a comma-separated list of discord user IDs
// I'm trying to split it and parse it into List<User?> from their IDs

var owners = dotenv["OWNERS"].split(",").forEach {
    // todo: implement
}

val botConfig = ProcyonConfig(
    token = dotenv["TOKEN"],
    prefix = dotenv["PREFIX"],
    owners = emptyList() // Was going to be `owners`
)