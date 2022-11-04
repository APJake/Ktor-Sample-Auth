package com.apjake.plugins.routing

import com.apjake.data.author.AuthorDataSource
import com.apjake.data.user.UserDataSource
import com.apjake.routes.user.changeUserRole
import com.apjake.routes.user.makeAuthor
import com.apjake.routes.user.deleteAuthor
import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.configureUserRouting(
    userDataSource: UserDataSource,
    authorDataSource: AuthorDataSource
) {
    routing {
        route("/api/v1/author") {
            makeAuthor(userDataSource, authorDataSource)
            deleteAuthor(userDataSource, authorDataSource)
        }
        route("/api/v1/user") {
            changeUserRole(userDataSource)
        }
    }
}