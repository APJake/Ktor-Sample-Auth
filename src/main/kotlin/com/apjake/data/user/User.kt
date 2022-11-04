package com.apjake.data.user

import com.apjake.data.author.Author
import com.apjake.plugins.pipelines.Role
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class User(
    @BsonId
    val id: ObjectId = ObjectId(),
    val username: String,
    val password: String,
    val salt: String,
    val author: Author? = null,
    val role: Role = Role.Guest
)