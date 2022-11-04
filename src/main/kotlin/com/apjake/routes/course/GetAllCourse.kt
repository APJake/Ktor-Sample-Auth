package com.apjake.routes.course

import com.apjake.data.course.CourseDataSource
import com.apjake.data.responses.BaseResponse
import com.apjake.data.responses.CoursesResponse
import com.apjake.mapper.CourseMapperWithParams
import com.apjake.utils.extensions.parseStringArray
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getCourses(
    courseDataSource: CourseDataSource
) {
    get {
        val query = call.request.queryParameters["q"] ?: ""
        val page = call.request.queryParameters["page"]?.toInt() ?: 0
        val limit = call.request.queryParameters["limit"]?.toInt() ?: 10
        val categories = call.request.queryParameters["categories"].parseStringArray()

        val courses = courseDataSource.getCourses(
            search = query,
            categories = categories,
            page = page,
            limit = limit
        )

        call.respond(
            HttpStatusCode.OK,
            BaseResponse.success(
                CoursesResponse(courses.map {
                    CourseMapperWithParams.getResponse(it)
                })
            )
        )
    }
}