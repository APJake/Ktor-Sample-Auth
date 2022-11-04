package com.apjake.routes.auth

import com.apjake.data.requests.AuthRequest
import com.apjake.data.responses.AuthResponse
import com.apjake.data.responses.BaseResponse
import com.apjake.data.user.UserDataSource
import com.apjake.security.hashing.HashingService
import com.apjake.security.hashing.SaltedHash
import com.apjake.security.token.TokenClaim
import com.apjake.security.token.TokenConfig
import com.apjake.security.token.TokenService
import com.apjake.utils.AppConstants
import com.apjake.utils.throwable.JakeThrowable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.signIn() {
    val userDataSource by inject<UserDataSource>()
    val hashingService by inject<HashingService>()
    val tokenService by inject<TokenService>()
    val tokenConfig by inject<TokenConfig>()

    post("signin") {
        val request = call.receiveNullable<AuthRequest>() ?: kotlin.run {
            throw JakeThrowable.badRequest
        }

        val user = userDataSource.getUserByUsername(
            request.username
        ) ?: throw JakeThrowable(
            "Incorrect username or password"
        )

        val isValidPassword = hashingService.verify(
            value = request.password,
            saltedHash = SaltedHash(
                hash = user.password,
                salt = user.salt
            )
        )
        if (!isValidPassword) {
            throw JakeThrowable("Incorrect username or password")
        }

        val token = tokenService.generate(
            config = tokenConfig,
            TokenClaim(
                name = AppConstants.TOKEN_KEY_USER_ID,
                value = user.id.toString()
            ),
            TokenClaim(
                name = AppConstants.TOKEN_KEY_USER_ROLE,
                value = user.role.roleStr
            )
        )

        call.respond(
            status = HttpStatusCode.OK,
            message = BaseResponse.success(
                AuthResponse(
                    token = token
                )
            )
        )
    }
}
