package bot.procyon.util

import bot.procyon.models.User
import bot.procyon.models.guild
import bot.procyon.models.user
import dev.kord.common.entity.Snowflake
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.Query
import org.komapper.core.dsl.query.singleOrNull
import org.komapper.r2dbc.R2dbcDatabase

val user =  Meta.user
val guild = Meta.guild

suspend fun R2dbcDatabase.getOrCreateUser(id: Snowflake): User {
    val selectQuery: Query<User?> = QueryDsl.from(user).where { user.id eq id }.singleOrNull()

    return runQuery { selectQuery }
        ?: this.runQuery {
            QueryDsl.insert(user).single(
                User(id, 0.00, 0)
            ).returning()
        }
}