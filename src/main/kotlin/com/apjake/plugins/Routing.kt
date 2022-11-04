package com.apjake.plugins

import com.apjake.routes.configureAuthRoutes
import com.apjake.routes.configureCourseRoutes
import com.apjake.routes.configureTestRoutes
import com.apjake.routes.configureUserRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRoutes() {
    // {{ routes }}
    routing {
        route("/api") {
            // auth
            configureAuthRoutes()
            // course
            configureCourseRoutes()
            // user
            configureUserRoutes()
            // test
            configureTestRoutes()
        }
    }
}