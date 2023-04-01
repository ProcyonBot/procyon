package bot.procyon.di

import dev.kord.core.Kord
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.runBlocking
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val botModule = module {
    fun provideToken() = dotenv().get("TOKEN")

    fun provideKord(token: String): Kord {
        return runBlocking { Kord(token) } // you should never do this in production code!!!!!!!!!!!!!!!!!!
    }

    // hi
    // you are scary
    // :)
    singleOf(::provideToken)
    singleOf(::provideKord)
}