package bot.procyon.di

import bot.procyon.commands.Command
import bot.procyon.util.ProcyonConfig
import com.charleskorn.kaml.Yaml
import dev.kord.core.Kord
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import kotlin.io.path.Path
import kotlin.io.path.notExists
import kotlin.io.path.readText

val botModule = module {
    fun provideConfig(): ProcyonConfig {
        val path = Path("config.yml")

        if (path.notExists()) error("Config file not found!")

        return Yaml.default.decodeFromString(path.readText())
    }

    fun provideKord(config: ProcyonConfig): Kord = runBlocking {
        Kord(config.token)
    }

    fun provideCommands(): List<Command> {
        return Command::class.sealedSubclasses.map {
            it::constructors.get().single().call()
        }
    }

    singleOf(::provideConfig)
    singleOf(::provideKord)
    singleOf(::provideCommands)
}