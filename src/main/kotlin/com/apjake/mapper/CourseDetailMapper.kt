package com.apjake.mapper

import com.apjake.data.course.Course
import com.apjake.data.responses.CourseDetailResponse
import com.apjake.utils.base.UniMapper

object CourseDetailMapper : UniMapper<Course, CourseDetailResponse>() {
    override fun map(data: Course): CourseDetailResponse {
        return with(data) {
            CourseDetailResponse(
                id = id.toHexString(),
                title = title,
                description = description,
                detailHTML = detailHTML,
                posterUrl = posterUrl,
                author = AuthorMapper.getResponse(author),
                type = type,
                price = price,
                categories = categories,
                rateAmount = 0,
                rateCounts = 0,
                studentCount = students.size.toLong(),
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}