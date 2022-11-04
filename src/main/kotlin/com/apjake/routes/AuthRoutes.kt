package com.apjake.routes

import com.apjake.routes.auth.authenticate
import com.apjake.routes.auth.getSecretInfo
import com.apjake.routes.auth.signIn
import com.apjake.routes.auth.signUp
import io.ktor.server.routing.*

fun Route.configureAuthRoutes() {
    route("/v1/auth") {
        signIn()
        signUp()
        authenticate()
        getSecretInfo()
    }
}