package bot.procyon.di

import dev.kord.core.Kord
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.runBlocking
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val botModule = module {
    fun provideDotenv() = dotenv()

    fun provideToken(dotenv: Dotenv) = dotenv["TOKEN"]

    fun provideKord(token: String): Kord = runBlocking {
        Kord(token)
    }

    singleOf(::provideDotenv)
    singleOf(::provideToken)
    singleOf(::provideKord)
}