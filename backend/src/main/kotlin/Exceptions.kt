package revolut.backend

abstract class BackendException(message: String, cause: Throwable? = null) : Throwable(message, cause)