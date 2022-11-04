package com.apjake.routes.course

import com.apjake.data.course.CourseDataSource
import com.apjake.data.requests.CourseRequest
import com.apjake.data.responses.BaseResponse
import com.apjake.data.responses.Nothing
import com.apjake.data.user.UserDataSource
import com.apjake.mapper.CourseMapper
import com.apjake.plugins.pipelines.Role
import com.apjake.plugins.pipelines.withAnyRole
import com.apjake.usecase.RetrieveAuthor
import com.apjake.utils.throwable.JakeThrowable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createCourse(
    courseDataSource: CourseDataSource,
    userDataSource: UserDataSource
) {
    authenticate {
        withAnyRole(*Role.courseCreatorRoles) {
            post {
                val author = RetrieveAuthor(
                    call.principal<JWTPrincipal>(),
                    userDataSource
                )

                val request = call.receiveNullable<CourseRequest>() ?: kotlin.run {
                    throw JakeThrowable("Invalid request")
                }

                if (!request.isValid) {
                    throw JakeThrowable(request.firstErrorMessage)
                }

                val course = CourseMapper.getModel(request, CourseMapper.Param(author))

                val wasAcknowledge = courseDataSource.createCourse(course)

                if (!wasAcknowledge) {
                    throw JakeThrowable("Failed to create course")
                }

                call.respond(
                    HttpStatusCode.OK,
                    BaseResponse.success<Nothing>()
                )
            }
        }
    }
}