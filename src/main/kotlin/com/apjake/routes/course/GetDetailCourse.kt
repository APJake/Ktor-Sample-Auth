package com.apjake.routes.course

import com.apjake.data.course.CourseDataSource
import com.apjake.data.responses.BaseResponse
import com.apjake.mapper.CourseDetailMapper
import com.apjake.utils.throwable.JakeThrowable
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.getDetailCourse() {
    val courseDataSource by inject<CourseDataSource>()
    get("{id}") {
        val courseId = call.parameters["id"] ?: kotlin.run {
            throw JakeThrowable.badRequest
        }
        val course = courseDataSource.getCourseDetail(courseId)
            ?: courseDataSource.getCourseByCode(courseId)
            ?: kotlin.run {
                throw JakeThrowable("No such course")
            }
        call.respond(
            BaseResponse.success(
                CourseDetailMapper(course)
            )
        )
    }
}