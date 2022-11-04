package com.apjake.routes

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