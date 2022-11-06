package com.apjake.routes.user

import com.apjake.data.responses.BaseResponse
import com.apjake.data.user.UserDataSource
import com.apjake.mapper.AuthorMapper
import com.apjake.utils.throwable.JakeThrowable
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Route.viewAuthorProfile() {
    val userDataSource by inject<UserDataSource>()
    get("{userid}") {
        val userId = call.parameters["userid"] ?: kotlin.run {
            throw JakeThrowable.badRequest
        }
        val user = userDataSource.getUserById(userId) ?: kotlin.run {
            throw JakeThrowable.noSuch("user")
        }
        val author = user.author ?: kotlin.run {
            throw JakeThrowable("Requested user has no author profile")
        }
        call.respond(
            BaseResponse.success(
                AuthorMapper.getResponse(author)
            )
        )
    }
}