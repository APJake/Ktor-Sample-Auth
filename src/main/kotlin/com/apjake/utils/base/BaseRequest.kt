package com.apjake.utils.base

import kotlinx.serialization.Serializable

@Serializable
abstract class BaseRequest {
    private val invalidMessageList = mutableListOf<String>()
    protected fun invalid(message: String) {
        invalidMessageList.add(message)
    }

    protected abstract val validator: () -> Unit
    val isValid: Boolean
        get() {
            validator()
            return invalidMessageList.isEmpty()
        }

    private fun safeMessageCall(block: () -> String): String {
        if (invalidMessageList.isEmpty()) return ""
        return block()
    }

    val firstErrorMessage: String
        get() = safeMessageCall { invalidMessageList.first() }
    val lastErrorMessage: String
        get() = safeMessageCall { invalidMessageList.last() }
    val allErrorMessage: String
        get() = safeMessageCall { invalidMessageList.joinToString(", ") }

    data class ValidResult(
        val isSuccess: Boolean,
        val message: String
    )
}