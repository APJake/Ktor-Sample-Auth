package com.apjake.routes.course

import com.apjake.data.course.CourseDataSource
import com.apjake.data.responses.BaseResponse
import com.apjake.data.responses.Nothing
import com.apjake.data.user.UserDataSource
import com.apjake.plugins.pipelines.Role
import com.apjake.plugins.pipelines.withAnyRole
import com.apjake.plugins.userRole
import com.apjake.usecase.RetrieveAuthor
import com.apjake.utils.throwable.JakeThrowable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.deleteCourse() {
    val courseDataSource by inject<CourseDataSource>()
    val userDataSource by inject<UserDataSource>()
    authenticate {
        withAnyRole(*Role.managerRoles) {
            delete("{id}") {
                val author = RetrieveAuthor(
                    call.principal<JWTPrincipal>(),
                    userDataSource
                )
                val userRole = call.principal<JWTPrincipal>()?.userRole ?: kotlin.run {
                    throw JakeThrowable.unauthorized
                }
                val courseId = call.parameters["id"] ?: kotlin.run {
                    throw JakeThrowable.badRequest
                }
                val course = courseDataSource.getCourseDetail(courseId) ?: kotlin.run {
                    throw JakeThrowable.noSuch("course to delete")
                }
                if (userRole == Role.Author.roleStr) {
                    if (course.author.id != author.id) {
                        // not the owner
                        throw JakeThrowable.unauthorized
                    }
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