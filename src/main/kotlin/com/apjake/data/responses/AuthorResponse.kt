package com.apjake.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class AuthorResponse(
    val id: String,
    val displayName: String,
    val role: String,
    val profileUrl: String,
    val description: String,
    val links: List<ContactResponse>
)