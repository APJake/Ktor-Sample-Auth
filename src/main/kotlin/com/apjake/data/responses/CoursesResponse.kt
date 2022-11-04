package com.apjake.data.responses
import kotlinx.serialization.Serializable

@Serializable
data class CoursesResponse(
    val courses: List<CourseResponse>
)

@Serializable
data class CourseResponse(
    val id: String,
    val code: String,
    val title: String,
    val description: String,
    val posterUrl: String,
    val authorName: String,
    val type: String,
    val price: Long,
    val categories: List<String>,
    val rateAmount: Long,
    val rateCounts: Long,
    val studentCount: Long,
)