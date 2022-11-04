package com.apjake.data.course

import com.apjake.data.author.Author
import com.apjake.data.user.User
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Course(
    @BsonId
    val id: ObjectId,
    val code: String,
    val title: String,
    val description: String,
    val detailHTML: String,
    val posterUrl: String,
    val author: Author,
    val type: String,
    val price: Long,
    val categories: List<String>,
    val rateAmount: Long = 0,
    val rateCounts: Long = 0,
    val students: List<User> = emptyList()
)

enum class CourseType(value: String) {
    Free("free"),
    Paid("paid");
}