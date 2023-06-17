package bot.procyon.models

import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId

@KomapperEntity
//@KomapperOneToMany(targetEntity = Guild::class)
data class User(
    @KomapperId
    val id:             Long,
    val balance:        Int,
    val exp:            Float
)

