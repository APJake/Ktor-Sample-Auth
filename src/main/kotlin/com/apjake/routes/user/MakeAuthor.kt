package com.apjake.routes.user

import com.apjake.data.author.AuthorDataSource
import com.apjake.data.requests.AuthorRequest
import com.apjake.data.responses.BaseResponse
import com.apjake.data.responses.Nothing
import com.apjake.data.user.UserDataSource
import com.apjake.mapper.AuthorMapper
import com.apjake.plugins.pipelines.Role
import com.apjake.plugins.pipelines.withAnyRole
import com.apjake.utils.throwable.JakeThrowable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.makeAuthor() {
    val userDataSource by inject<UserDataSource>()
    val authorDataSource by inject<AuthorDataSource>()

    authenticate {
        withAnyRole(*Role.managerRoles) {
            post("{id}") {
                val targetUserId = call.parameters["id"] ?: kotlin.run {
                    throw JakeThrowable("Invalid request")
                }
                val request = call.receiveNullable<AuthorRequest>() ?: kotlin.run {
                    throw JakeThrowable("Invalid request")
                }

                if (!request.isValid) {
                    throw JakeThrowable(
                        message = request.firstErrorMessage,
                        detail = request.allErrorMessage
                    )
                }
                val author = AuthorMapper.getModel(request)

                val userWithAuthorName = authorDataSource.getUserByAuthorName(author.displayName)

                if (userWithAuthorName != null) {
                    throw JakeThrowable("Author name already exists")
                }

                val targetUser = userDataSource.getUserById(targetUserId) ?: kotlin.run {
                    throw JakeThrowable(
                        message = "Invalid target user",
                    )
                }

                val wasAcknowledge = authorDataSource.makeAuthor(targetUser, author)

                if (!wasAcknowledge) {
                    throw JakeThrowable("Failed to make author")
                }

                call.respond(
                    HttpStatusCode.OK,
                    BaseResponse.success<Nothing>()
                )
            }
        }
    }
}