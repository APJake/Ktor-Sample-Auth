package com.apjake.routes.auth

import com.apjake.data.requests.AuthRequest
import com.apjake.data.responses.BaseResponse
import com.apjake.data.responses.Nothing
import com.apjake.data.user.User
import com.apjake.data.user.UserDataSource
import com.apjake.plugins.pipelines.Role
import com.apjake.security.hashing.HashingService
import com.apjake.utils.throwable.JakeThrowable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.signUp(
    hashingService: HashingService,
    userDataSource: UserDataSource
) {
    post("signup") {
        val request = call.receiveNullable<AuthRequest>() ?: kotlin.run {
            throw JakeThrowable.badRequest
        }

        if (!request.isValid) {
            throw JakeThrowable(request.firstErrorMessage)
        }

        val saltedHash = hashingService.generateSaltedHash(request.password)
        val user = User(
            username = request.username,
            password = saltedHash.hash,
            salt = saltedHash.salt,
            role = Role.Guest
        )

        val wasAcknowledged = userDataSource.insertUser(user)
        if (!wasAcknowledged) {
            throw JakeThrowable("Failed to register")
        }

        call.respond(
            HttpStatusCode.OK,
            BaseResponse.success<Nothing>()
        )
    }
}
