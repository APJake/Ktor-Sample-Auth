package com.apjake.plugins

import com.apjake.plugins.pipelines.Role
import com.apjake.plugins.pipelines.RoleBasedAuthorization
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*

fun Application.configureAuthorization() {
    install(RoleBasedAuthorization) {
        getRoles { data ->
            val principal = data as JWTPrincipal
            val role = Role.getRoleOrNull(principal.userRole)
            val roles = role?.let {
                setOf(it)
            } ?: setOf()
            roles
        }
    }
}