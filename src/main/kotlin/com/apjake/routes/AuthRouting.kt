package com.apjake.routes

import com.apjake.data.user.UserDataSource
import com.apjake.routes.auth.authenticate
import com.apjake.routes.auth.getSecretInfo
import com.apjake.routes.auth.signIn
import com.apjake.routes.auth.signUp
import com.apjake.security.hashing.HashingService
import com.apjake.security.token.TokenConfig
import com.apjake.security.token.TokenService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureAuthRouting(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig,
) {
    routing {
        route("/api/v1/auth") {
            signIn(hashingService, userDataSource, tokenService, tokenConfig)
            signUp(hashingService, userDataSource)
            authenticate()
            getSecretInfo()
        }
    }
}