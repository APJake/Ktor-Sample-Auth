package com.apjake.plugins

import com.apjake.data.author.AuthorDataSource
import com.apjake.data.course.CourseDataSource
import com.apjake.data.user.UserDataSource
import com.apjake.routes.configureAuthRoutes
import com.apjake.routes.configureCourseRoutes
import com.apjake.routes.configureTestRoutes
import com.apjake.routes.configureUserRoutes
import com.apjake.security.hashing.HashingService
import com.apjake.security.token.TokenConfig
import com.apjake.security.token.TokenService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRoutes() {

    // injection
    val userDataSource by inject<UserDataSource>()
    val hashingService by inject<HashingService>()
    val tokenService by inject<TokenService>()
    val tokenConfig by inject<TokenConfig>()
    val courseDataSource by inject<CourseDataSource>()
    val authorDataSource by inject<AuthorDataSource>()

    // {{ routes }}
    routing {
        route("/api") {
            // auth
            configureAuthRoutes(userDataSource, hashingService, tokenService, tokenConfig)
            // course
            configureCourseRoutes(courseDataSource, userDataSource)
            // user
            configureUserRoutes(userDataSource, authorDataSource)
            // test
            configureTestRoutes()
        }
    }
}