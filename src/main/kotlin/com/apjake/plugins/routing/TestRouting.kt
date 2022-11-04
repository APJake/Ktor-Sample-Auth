package com.apjake.plugins.routing

import com.apjake.routes.test.testRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.configureTestRouting() {
    routing {
        route("/test") {
            testRoute()
        }
    }
}