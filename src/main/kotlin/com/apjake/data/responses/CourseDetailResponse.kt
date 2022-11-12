package com.apjake.data.responses

import kotlinx.serialization.Serializable

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
    val createdAt: Long,
    val updatedAt: Long
)