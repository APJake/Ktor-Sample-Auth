package com.apjake.mapper

import com.apjake.data.author.Author
import com.apjake.data.course.Course
import com.apjake.data.requests.CourseRequest
import com.apjake.data.responses.CourseResponse
import com.apjake.utils.base.Params
import com.apjake.utils.base.TriMapperWithParams

object CourseMapper : TriMapperWithParams<CourseRequest, Course, CourseResponse, CourseMapper.Param> {
    override fun getModel(request: CourseRequest, param: Param): Course {
        return with(request) {
            Course(
                title = title,
                description = description,
                detailHTML = detailHTML,
                posterUrl = posterUrl,
                author = param.author,
                type = type,
                price = price ?: 0,
                categories = categories,
                rateAmount = 0,
                rateCounts = 0,
                students = emptyList()
            )
        }
    }

    override fun getResponse(model: Course): CourseResponse {
        return with(model) {
            CourseResponse(
                id = id.toHexString(),
                title = title,
                description = description,
                posterUrl = posterUrl,
                authorName = author.displayName,
                type = type,
                price = price,
                categories = categories,
                rateAmount = 0,
                rateCounts = 0,
                studentCount = students.size.toLong()
            )
        }
    }

    data class Param(
        val author: Author
    ) : Params
}
