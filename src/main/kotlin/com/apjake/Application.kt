package com.apjake

import com.apjake.data.user.User
import com.apjake.data.user.UserDataSource
import com.apjake.data.user.UserDataSourceImpl
import io.ktor.server.application.*
import com.apjake.plugins.*
import com.apjake.security.hashing.SHA256HashingService
import com.apjake.security.token.JwtTokenService
import com.apjake.security.token.TokenConfig
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    val mongoUsername = System.getenv("MONGO_USERNAME")
    val mongoPwd = System.getenv("MONGO_PASSWORD")
    val dbName = "ktor-sample-auth"
    val connectionString = "mongodb+srv://$mongoUsername:$mongoPwd@clusterjake.j0u2xmu.mongodb.net/" +
            "$dbName?retryWrites=true&w=majority"

    val db = KMongo
        .createClient(connectionString)
        .coroutine
        .getDatabase(dbName)

    val userDataSource = UserDataSourceImpl(db)
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
    configureRouting(userDataSource, hashingService, tokenService, tokenConfig)
}