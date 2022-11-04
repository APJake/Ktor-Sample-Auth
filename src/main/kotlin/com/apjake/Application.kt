package com.apjake

import com.apjake.data.author.AuthorDataSourceImpl
import com.apjake.data.course.CourseDataSourceImpl
import com.apjake.data.user.UserDataSourceImpl
import io.ktor.server.application.*
import com.apjake.plugins.*
import com.apjake.routes.configureAuthRouting
import com.apjake.routes.configureCourseRouting
import com.apjake.routes.configureTestRouting
import com.apjake.routes.configureUserRouting
import com.apjake.security.hashing.SHA256HashingService
import com.apjake.security.token.JwtTokenService
import com.apjake.security.token.TokenConfig

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    // database setup
    val db = configureDatabase()

    // data sources
    val userDataSource = UserDataSourceImpl(db)
    val courseDataSource = CourseDataSourceImpl(db)
    val authorDataSource = AuthorDataSourceImpl(db)

    // services
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = SHA256HashingService()

    configureSerialization()
    configureMonitoring()
    configureSecurity(tokenConfig)
    configureAuthorization()

    // {{ routes }}
    // auth
    configureAuthRouting(userDataSource, hashingService, tokenService, tokenConfig)
    // course
    configureCourseRouting(courseDataSource, userDataSource)
    // user
    configureUserRouting(userDataSource, authorDataSource)
    // test
    configureTestRouting()

    // exception
    configureExceptions()
}