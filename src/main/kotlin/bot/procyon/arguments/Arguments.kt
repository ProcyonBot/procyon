package bot.procyon.arguments

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

// Future file for argument parsing

abstract class Arguments {
    val arguments: List<Argument<*>>
        get() = emptyList()

    fun parse(args: List<String>): Argument<*>  {
        TODO()
    }
}

interface Argument<T> : ReadOnlyProperty<Arguments, T> {
    var value: T
        get() = TODO()
        set(value) = TODO()

    fun optional() {

    }
    fun default(default: T) {

    }

    override fun getValue(thisRef: Arguments, property: KProperty<*>): T {
        return value
    }
}

fun <T> Arguments.argument(): Argument<T> {
    return object : Argument<T> {

    }
}

abstract class Test<T : Arguments>(args: () -> T) {
    abstract fun exec(arguments: T)
}

class Gorb : Test<Gorb.TestArgs>(::TestArgs) {
    class TestArgs : Arguments() {
        val test: String by argument()
    }

    override fun exec(arguments: TestArgs) {
        println(arguments.test)
    }
}