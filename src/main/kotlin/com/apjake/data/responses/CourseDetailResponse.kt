package com.apjake.data.responses

import com.apjake.data.author.Author
import com.apjake.data.user.User
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class CourseDetailResponse(
    val id: String,
    val title: String,
    val description: String,
    val detailHTML: String,
    val posterUrl: String,
    val author: AuthorResponse,
    val type: String,
    val price: Long,
    val categories: List<String>,
    val rateAmount: Long,
    val rateCounts: Long,
    val studentCount: Long,
)