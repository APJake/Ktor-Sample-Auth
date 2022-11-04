package com.apjake.data.author

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Author(
    @BsonId
    val id: ObjectId = ObjectId(),
    val displayName: String,
    val role: String,
    val profileUrl: String,
    val description: String,
    val links: List<ContactLink> = emptyList()
)

data class ContactLink(
    val key: String,
    val url: String
)