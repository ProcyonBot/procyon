package bot.procyon.models

import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId

@KomapperEntity
//@KomapperOneToMany(targetEntity = User::class)
data class Guild(
    @KomapperId
    val id:     Long
)
