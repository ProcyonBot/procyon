package bot.procyon.models

import dev.kord.common.entity.Snowflake
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperOneToMany
import org.komapper.annotation.KomapperTable

@KomapperEntity
@KomapperOneToMany(targetEntity = Guild::class)
@KomapperTable(alwaysQuote = true)
data class User(
    @KomapperId
    val id:             Snowflake,
    val balance:        Int,
    val exp:            Int
)

