package bot.procyon.models

import dev.kord.common.entity.Snowflake
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperOneToMany

@KomapperEntity
@KomapperOneToMany(targetEntity = User::class)
data class Guild(
    @KomapperId
    val id:     Snowflake
)
