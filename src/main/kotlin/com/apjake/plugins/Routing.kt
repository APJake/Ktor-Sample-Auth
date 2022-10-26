package com.apjake.plugins

import com.apjake.*
import com.apjake.data.user.UserDataSource
import com.apjake.security.hashing.HashingService
import com.apjake.security.token.TokenConfig
import com.apjake.security.token.TokenService
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    routing {
        signIn(hashingService, userDataSource, tokenService, tokenConfig)
        signUp(hashingService, userDataSource)
        authenticate()
        getSecretInfo()
    }
}
