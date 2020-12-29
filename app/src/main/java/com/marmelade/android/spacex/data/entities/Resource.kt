package com.marmelade.android.spacex.data.entities


/**
 * Wrapper for API responses
 *
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
class Resource<out T> private constructor(
        val status: Status,
        val data: T?,
        val errorIdentification: ErrorIdentification = ErrorIdentification.None()
) {
    companion object {
        fun <T> notStarted(data: T? = null): Resource<T> = Resource(NotStartedStatus(), data)

        fun <T> success(data: T?): Resource<T> = Resource(SuccessStatus(), data)

        fun <T> error(errorIdentification: ErrorIdentification, data: T? = null): Resource<T> =
                Resource(ErrorStatus(), data, errorIdentification)

        fun <T> loading(data: T? = null): Resource<T> = Resource(LoadingStatus(), data)
    }
}

sealed class Status
class NotStartedStatus : Status()
class SuccessStatus : Status()
class ErrorStatus : Status()
class LoadingStatus : Status()

sealed class ErrorIdentification(val message: String) {
    class None : ErrorIdentification("")
    class Unknown(message: String = "") : ErrorIdentification(message)
    class Authentication(message: String = "") : ErrorIdentification(message)
    class Connection(message: String = "") : ErrorIdentification(message)
    class NotFound(message: String = "") : ErrorIdentification(message)
    class InternalServerError(message: String = "") : ErrorIdentification(message)
    class Timeout(message: String = "") : ErrorIdentification(message)

    fun parseLog() = "${this.javaClass.simpleName}: $message"
}