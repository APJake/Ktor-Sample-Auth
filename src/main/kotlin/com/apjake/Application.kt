package com.apjake

import com.apjake.di.dataSourceModule
import com.apjake.di.serviceModule
import com.apjake.plugins.*
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    install(Koin) {
        slf4jLogger()
        modules(
            serviceModule(environment),
            dataSourceModule,
        )
    }

    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureAuthorization()
    configureRoutes()

    // exception
    configureExceptions()
}