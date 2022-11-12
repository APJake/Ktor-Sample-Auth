package com.apjake.data.course

import com.apjake.data.author.Author
import com.apjake.data.user.User
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import kotlin.reflect.KProperty

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
    val students: List<User> = emptyList(),
    val createdAt: Long,
    val updatedAt: Long
)

enum class CourseType(value: String) {
    Free("free"),
    Paid("paid");
}

enum class SortBy {
    Name, NameDesc, Date, DateDesc,
    Price, PriceDesc, Rating, RatingDesc;

    val property: KProperty<*>
        get() {
            return when (this) {
                Name -> Course::title
                NameDesc -> Course::title
                Date -> Course::createdAt
                DateDesc -> Course::createdAt
                Price -> Course::price
                PriceDesc -> Course::price
                Rating -> Course::rateCounts
                RatingDesc -> Course::rateCounts
            }
        }
    val isDescending: Boolean
        get() = this == NameDesc || this == DateDesc
                || this == PriceDesc || this == RatingDesc
}