package com.apjake.routes

import com.apjake.routes.user.changeUserRole
import com.apjake.routes.user.deleteAuthor
import com.apjake.routes.user.makeAuthor
import com.apjake.routes.user.viewAuthorProfile
import io.ktor.server.routing.*

fun Route.configureUserRoutes() {
    route("/v1/author") {
        makeAuthor()
        deleteAuthor()
        viewAuthorProfile()
    }
    route("/v1/user") {
        changeUserRole()
    }
}