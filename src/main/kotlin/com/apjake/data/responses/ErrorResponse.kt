package com.apjake.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val code: Int,
    val message: String,
    val detail: String? = null
)