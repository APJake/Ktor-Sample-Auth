package com.apjake.routes.error

import com.apjake.utils.throwable.JakeThrowable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.exceptionRoutes() {
    get("/validation") {
        throw JakeThrowable("Validation exception")
    }

    get("/parsing") {
        throw JakeThrowable("Parsing exception")
    }
}

fun Route.statusRoutes() {
    get("/internal-error") {
        call.respond(
            HttpStatusCode.InternalServerError
        )
    }
    get("/bad-gateway") {
        call.respond(
            HttpStatusCode.BadGateway
        )
    }
}
