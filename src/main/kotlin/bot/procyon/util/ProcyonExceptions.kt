package bot.procyon.util

open class ProcyonCommandException(cmdName: String? = null, message: String? = null, cause: Throwable? = null) : Exception(message, cause)

class ProcyonNeedsArgsException(cmdName: String? = null, message: String? = "Command $cmdName requires arguments", cause: Throwable? = null)
    : ProcyonCommandException(cmdName, message, cause)

class ProcyonDisabledException(cmdName: String? = null, message: String? = "Command $cmdName is disabled", cause: Throwable? = null)
    : ProcyonCommandException(cmdName, message, cause)

class ProcyonChecksException(cmdName: String? = null, message: String? = "Command $cmdName failed checks", cause: Throwable? = null)
    : ProcyonCommandException(cmdName, message, cause)

class ProcyonCooldownException(cmdName: String? = null, message: String? = "Command $cmdName is on cooldown", cause: Throwable? = null)
    : ProcyonCommandException(cmdName, message, cause)