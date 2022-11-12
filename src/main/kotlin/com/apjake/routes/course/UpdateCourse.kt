package com.apjake.routes.course

import com.apjake.data.course.CourseDataSource
import com.apjake.data.requests.CourseRequest
import com.apjake.data.responses.BaseResponse
import com.apjake.data.user.UserDataSource
import com.apjake.mapper.CourseMapper
import com.apjake.plugins.pipelines.Role
import com.apjake.plugins.pipelines.withAnyRole
import com.apjake.plugins.userId
import com.apjake.plugins.userRole
import com.apjake.usecase.RetrieveAuthor
import com.apjake.utils.helper.DateHelper
import com.apjake.utils.throwable.JakeThrowable
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.types.ObjectId
import org.koin.ktor.ext.inject


fun Route.updateCourse() {
    val userDataSource by inject<UserDataSource>()
    val courseDataSource by inject<CourseDataSource>()

    authenticate {
        withAnyRole(*Role.courseCreatorRoles) {
            put("{id}") {
                val author = RetrieveAuthor(
                    call.principal<JWTPrincipal>(),
                    userDataSource
                )

                val userId = call.principal<JWTPrincipal>()?.userId ?: kotlin.run {
                    throw JakeThrowable.unauthorized
                }
                val userRole = call.principal<JWTPrincipal>()?.userRole ?: kotlin.run {
                    throw JakeThrowable.unauthorized
                }

                val courseId = call.parameters["id"] ?: kotlin.run {
                    throw JakeThrowable.badRequest
                }

                val request = call.receiveNullable<CourseRequest>() ?: kotlin.run {
                    throw JakeThrowable("Invalid request")
                }

                if (!request.isValid) {
                    throw JakeThrowable(request.firstErrorMessage)
                }

                val course = CourseMapper
                    .getModel(request, CourseMapper.Param(author))
                    .copy(
                        id = ObjectId(courseId),
                        updatedAt = DateHelper.nowTimestamp
                    )

                val existingCourse = courseDataSource.getCourseDetail(courseId) ?: kotlin.run {
                    throw JakeThrowable.noSuch("course to update")
                }

                if (userRole == Role.Author.roleStr) {
                    if (existingCourse.author.id != author.id) {
                        // not the owner
                        throw JakeThrowable.unauthorized
                    }
                }

                if (existingCourse.code != course.code) {
                    val courseByCode = courseDataSource.getCourseByCode(course.code)
                    if (courseByCode != null) {
                        throw JakeThrowable("Course code already exists")
                    }
                }

                val wasAcknowledge = courseDataSource.updateCourse(course)

                if (!wasAcknowledge) {
                    throw JakeThrowable.failedTo("update course")
                }

                call.respond(
                    BaseResponse.success(
                        CourseMapper.getResponse(course)
                    )
                )
            }
        }
    }
}