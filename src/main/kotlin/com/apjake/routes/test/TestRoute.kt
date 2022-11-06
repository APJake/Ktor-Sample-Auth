package com.apjake.routes.test

import com.apjake.plugins.pipelines.Role
import com.apjake.plugins.pipelines.withRole
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.testRoute(){
    authenticate {
        get {
            call.respond("It's working")
        }
        withRole(Role.Admin){
            get("role") {
                call.respond("I am admin")
            }
        }
    }
}