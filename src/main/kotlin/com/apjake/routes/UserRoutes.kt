package com.apjake.routes

import com.apjake.data.author.AuthorDataSource
import com.apjake.data.user.UserDataSource
import com.apjake.routes.user.changeUserRole
import com.apjake.routes.user.deleteAuthor
import com.apjake.routes.user.makeAuthor
import io.ktor.server.routing.*


fun Route.configureUserRoutes(
    userDataSource: UserDataSource,
    authorDataSource: AuthorDataSource
) {
    route("/v1/author") {
        makeAuthor(userDataSource, authorDataSource)
        deleteAuthor(userDataSource, authorDataSource)
    }
    route("/v1/user") {
        changeUserRole(userDataSource)
    }
}