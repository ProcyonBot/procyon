package bot.procyon.models

import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperOneToMany

@KomapperEntity
@KomapperOneToMany(targetEntity = User::class)
data class Guild(
    @KomapperId
    val id:     Long
)
