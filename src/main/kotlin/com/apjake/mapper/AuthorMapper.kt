package com.apjake.mapper

import com.apjake.data.author.Author
import com.apjake.data.requests.AuthorRequest
import com.apjake.data.responses.AuthorResponse
import com.apjake.utils.base.TriMapper

object AuthorMapper : TriMapper<AuthorRequest, Author, AuthorResponse> {
    override fun getModel(request: AuthorRequest): Author {
        return with(request) {
            Author(
                displayName = displayName,
                role = role,
                description = description,
                profileUrl = profileUrl,
                links = links.map { ContactLinkRequestMapper.getModel(it) }
            )
        }
    }

    override fun getResponse(model: Author): AuthorResponse {
        return with(model) {
            AuthorResponse(
                id = id.toHexString(),
                displayName = displayName,
                role = role,
                description = description,
                profileUrl = profileUrl,
                links = links.map { ContactLinkRequestMapper.getResponse(it) }
            )
        }
    }
}