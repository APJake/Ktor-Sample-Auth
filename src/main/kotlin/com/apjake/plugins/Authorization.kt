package com.apjake.plugins

import com.apjake.plugins.pipelines.Role
import com.apjake.plugins.pipelines.RoleBasedAuthorization
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*


//fun Application.configureAuthorization() {
//    install(RoleAuthorization) {
//        validate {role,  allowedRoles ->
//            if (allowedRoles.contains(role)) {
//                Unit.right()
//            }
//            val userId = principal<JWTPrincipal>()?.userId
//            val userRole = principal<JWTPrincipal>()?.userRole
//            "userid: $userId, userRole: $userRole | Role $role not allowed".left()
//        }
//    }
//}
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