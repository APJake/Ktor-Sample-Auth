package com.apjake.routes

import com.apjake.routes.user.*
import io.ktor.server.routing.*

fun Route.configureUserRoutes() {
    route("/v1/author") {
        makeAuthor()
        deleteAuthor()
        updateAuthor()
        viewAuthorProfile()
    }
    route("/v1/user") {
        changeUserRole()
    }
}