package com.apjake.routes.user

import com.apjake.data.author.AuthorDataSource
import com.apjake.data.requests.AuthorRequest
import com.apjake.data.responses.BaseResponse
import com.apjake.data.user.UserDataSource
import com.apjake.mapper.AuthorMapper
import com.apjake.plugins.pipelines.Role
import com.apjake.plugins.pipelines.withAnyRole
import com.apjake.plugins.userId
import com.apjake.utils.throwable.JakeThrowable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.updateAuthor() {
    val userDataSource by inject<UserDataSource>()
    val authorDataSource by inject<AuthorDataSource>()

    authenticate {
        withAnyRole(*Role.courseCreatorRoles) {
            put("{id}") {
                val targetUserId = call.parameters["id"] ?: kotlin.run {
                    throw JakeThrowable("Invalid request")
                }

                // checking self permission
                if (targetUserId != call.principal<JWTPrincipal>()?.userId) {
                    throw JakeThrowable.unauthorized
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
                var author = AuthorMapper.getModel(request)

                val targetUser = userDataSource.getUserById(targetUserId) ?: kotlin.run {
                    throw JakeThrowable(
                        message = "Invalid target user",
                    )
                }

                if (targetUser.author?.displayName != author.displayName) {
                    val userWithAuthorName = authorDataSource.getUserByAuthorName(author.displayName)

                    if (userWithAuthorName != null) {
                        throw JakeThrowable("Author name already exists")
                    }
                }

                targetUser.author?.let {
                    author = author.copy(
                        id = it.id
                    )
                }

                val wasAcknowledge = authorDataSource.updateAuthor(targetUser, author)

                if (!wasAcknowledge) {
                    throw JakeThrowable.failedTo("update author")
                }

                call.respond(
                    HttpStatusCode.OK,
                    BaseResponse.success(true)
                )
            }
        }
    }
}