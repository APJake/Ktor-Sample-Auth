package com.apjake.routes.auth

import com.apjake.data.responses.BaseResponse
import com.apjake.plugins.userId
import com.apjake.plugins.userRole
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.getSecretInfo() {
    authenticate {
        get("secret") {
            val principle = call.principal<JWTPrincipal>()
            val userId = principle?.userId
            val userRole = principle?.userRole
            call.respond(
                HttpStatusCode.OK,
                BaseResponse.success("Your user ID is $userId and role is $userRole")
            )
        }
    }
}

fun Route.authenticate() {
    authenticate {
        get("authenticate") {
            call.respond(
                HttpStatusCode.OK,
                BaseResponse.success<Nothing>()
            )
        }
    }
}
