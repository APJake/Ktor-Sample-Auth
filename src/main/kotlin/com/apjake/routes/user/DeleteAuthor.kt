package com.apjake.routes.user

import com.apjake.data.author.AuthorDataSource
import com.apjake.data.responses.BaseResponse
import com.apjake.data.user.UserDataSource
import com.apjake.plugins.pipelines.Role
import com.apjake.plugins.pipelines.withAnyRole
import com.apjake.plugins.pipelines.withRole
import com.apjake.utils.throwable.JakeThrowable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteAuthor(
    userDataSource: UserDataSource,
    authorDataSource: AuthorDataSource
) {
    authenticate {
        withAnyRole(*Role.managerRoles) {
            delete("{id}") {
                val targetUserId = call.parameters["id"] ?: kotlin.run {
                    throw JakeThrowable("Invalid request")
                }

                val targetUser = userDataSource.getUserById(targetUserId) ?: kotlin.run {
                    throw JakeThrowable(
                        message = "Invalid target user",
                    )
                }

                val wasAcknowledge = authorDataSource.removeAuthor(targetUser)

                if (!wasAcknowledge) {
                    throw JakeThrowable("Failed to remove author")
                }

                call.respond(
                    HttpStatusCode.OK,
                    BaseResponse.success(true)
                )
            }
        }
    }
}