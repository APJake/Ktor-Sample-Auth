package com.apjake.utils.throwable

data class JakeThrowable(
    override val message: String,
    val code: Int = 400,
    val throwable: Throwable? = null,
    val detail: String? = null
) : Throwable(message, throwable) {
    companion object {
        val unknownError = JakeThrowable(
            code = 400,
            message = "Unknown error",
        )
        val badRequest = JakeThrowable(
            code = 400,
            message = "Bad request"
        )
        val conflict = JakeThrowable(
            code = 409,
            message = "Conflict"
        )
        val unauthorized = JakeThrowable(
            code = 403,
            message = "Unauthorized"
        )
        fun failedTo(message: String) = JakeThrowable(
            code = 400,
            message = "Failed to $message",
        )
    }
}