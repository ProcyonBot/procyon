package bot.procyon.models

import dev.kord.common.entity.Snowflake
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperOneToMany

@KomapperEntity
@KomapperOneToMany(targetEntity = Guild::class)
data class User(
    @KomapperId
    val id:             Snowflake,
    val balance:        Int,
    val exp:            Float
)
