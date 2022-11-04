package com.apjake.mapper

import com.apjake.data.author.ContactLink
import com.apjake.data.requests.ContactLinkRequest
import com.apjake.data.responses.ContactResponse
import com.apjake.utils.base.TriMapper

object ContactLinkRequestMapper : TriMapper<ContactLinkRequest, ContactLink, ContactResponse> {
    override fun getModel(request: ContactLinkRequest): ContactLink {
        return with(request) {
            ContactLink(
                key = key,
                url = url
            )
        }
    }

    override fun getResponse(model: ContactLink): ContactResponse {
        return with(model) {
            ContactResponse(
                key = key,
                url = url
            )
        }
    }
}