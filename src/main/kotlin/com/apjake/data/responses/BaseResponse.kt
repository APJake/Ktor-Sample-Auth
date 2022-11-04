package com.apjake.data.responses

import com.apjake.utils.throwable.JakeThrowable
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<Data>(
    val success: Boolean,
    val data: Data?,
    val error: ErrorResponse?,
) {
    companion object {
        fun error(
            code: Int,
            message: String,
            detail: String? = null
        ) = BaseResponse<Nothing>(
            success = false,
            data = null,
            error = ErrorResponse(
                code = code,
                message = message,
                detail = detail
            )
        )

        fun fromThrowable(err: JakeThrowable) = error(
            code = err.code,
            message = err.message,
            detail = err.detail ?: err.throwable?.localizedMessage
        )

        fun fromThrowable(err: Throwable) = error(
            code = 500,
            message = err.message ?: "Internal server error",
            detail = err.localizedMessage
        )

        fun <Data> success(data: Data? = null): BaseResponse<Data> {
            return BaseResponse(
                success = true,
                data = data,
                error = null
            )
        }
    }
}

@Serializable
data class Nothing(
    val data: String? = null
)