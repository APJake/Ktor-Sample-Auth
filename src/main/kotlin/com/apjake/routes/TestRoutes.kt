package com.apjake.routes

import com.apjake.routes.test.testRoute
import io.ktor.server.routing.*


fun Route.configureTestRoutes() {
    route("/test") {
        testRoute()
    }
}