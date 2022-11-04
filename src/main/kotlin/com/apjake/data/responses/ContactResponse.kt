package com.apjake.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class ContactResponse(
    val key: String,
    val url: String
)
