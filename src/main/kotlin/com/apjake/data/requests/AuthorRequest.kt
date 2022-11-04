package com.apjake.data.requests

import com.apjake.utils.base.BaseRequest
import com.apjake.utils.validators.IsValidUrl
import kotlinx.serialization.Serializable

@Serializable
data class AuthorRequest(
    val displayName: String,
    val role: String,
    val description: String,
    val profileUrl: String,
    val links: List<ContactLinkRequest> = emptyList()
) : BaseRequest() {
    override val validator = {
        if (displayName.isBlank() && role.isBlank() || description.isBlank())
            invalid("Fields shouldn't be empty")

        if (IsValidUrl(profileUrl))
            invalid("Invalid profile url")

        if (links.any { !it.isValid }) {
            invalid("Invalid links")
        }
    }
}

@Serializable
data class ContactLinkRequest(
    val key: String,
    val url: String
) : BaseRequest() {
    override val validator = {
        if (key.isBlank() || url.isBlank()
            || !IsValidUrl(url)
        )
            invalid("Invalid url $url with key $key")
    }
}