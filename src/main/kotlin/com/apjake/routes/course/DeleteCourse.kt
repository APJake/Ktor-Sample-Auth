package com.apjake.routes.course

import com.apjake.data.course.CourseDataSource
import com.apjake.data.responses.BaseResponse
import com.apjake.data.responses.Nothing
import com.apjake.plugins.pipelines.Role
import com.apjake.plugins.pipelines.withAnyRole
import com.apjake.utils.throwable.JakeThrowable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.deleteCourse() {
    val courseDataSource by inject<CourseDataSource>()
    authenticate {
        withAnyRole(*Role.managerRoles) {
            delete("{id}") {
                val courseId = call.parameters["id"] ?: kotlin.run {
                    throw JakeThrowable.badRequest
                }
                val wasAcknowledge = courseDataSource.deleteCourseById(courseId)
                if (!wasAcknowledge) {
                    throw JakeThrowable("Failed to delete course")
                }
                call.respond(
                    HttpStatusCode.OK,
                    BaseResponse.success<Nothing>()
                )
            }
        }
    }
}