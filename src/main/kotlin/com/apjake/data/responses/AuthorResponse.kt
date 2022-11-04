package com.apjake.data.responses

data class AuthorResponse(
    val id: String,
    val displayName: String,
    val role: String,
    val profileUrl: String,
    val description: String,
    val links: List<ContactResponse>
)