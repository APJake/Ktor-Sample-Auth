package com.apjake.plugins

import com.apjake.data.responses.BaseResponse
import com.apjake.utils.throwable.JakeThrowable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureExceptions() {
    install(StatusPages) {
        exception<JakeThrowable>() { call, throwable ->
            call.respond(
                HttpStatusCode.fromValue(
                    throwable.code
                ),
                BaseResponse.fromThrowable(throwable)
            )
        }
        exception<Throwable>() { call, throwable ->
            call.respond(
                HttpStatusCode.InternalServerError,
                BaseResponse.fromThrowable(throwable)
            )
        }
        status(
            HttpStatusCode.InternalServerError,
            HttpStatusCode.BadGateway,
        ) { call, statusCode ->
            when (statusCode) {
                HttpStatusCode.NotFound -> {
                    call.respond(
                        HttpStatusCode.NotFound,
                        BaseResponse.error(
                            HttpStatusCode.NotFound.value,
                            "Oops!!!, 404 not found!"
                        )
                    )
                }

                HttpStatusCode.InternalServerError -> {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        BaseResponse.error(
                            HttpStatusCode.InternalServerError.value,
                            "Oops!!!, Internal server error. We're on it. Try again later!"
                        )
                    )
                }

                HttpStatusCode.BadGateway -> {
                    call.respond(
                        HttpStatusCode.BadGateway,
                        BaseResponse.error(
                            HttpStatusCode.BadGateway.value,
                            "Oops!!!, We got a bad gateway. Try again later!"
                        )
                    )
                }
            }
        }
    }
}