package com.apjake.routes.user

import com.apjake.data.requests.UserRoleRequest
import com.apjake.data.responses.BaseResponse
import com.apjake.data.responses.Nothing
import com.apjake.data.user.UserDataSource
import com.apjake.plugins.pipelines.Role
import com.apjake.plugins.pipelines.withAnyRole
import com.apjake.plugins.userRole
import com.apjake.utils.helper.ge
import com.apjake.utils.helper.lt
import com.apjake.utils.throwable.JakeThrowable
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.changeUserRole(
    userDataSource: UserDataSource
) {
    authenticate {
        withAnyRole(*Role.managerRoles) {
            put("{id}/role") {
                val userRole = Role.getRoleOrNull(call.principal<JWTPrincipal>()?.userRole) ?: kotlin.run {
                    throw JakeThrowable.unauthorized
                }
                val targetId = call.parameters["id"] ?: kotlin.run {
                    throw JakeThrowable.badRequest
                }
                val request = call.receiveNullable<UserRoleRequest>() ?: kotlin.run {
                    throw JakeThrowable.badRequest
                }
                if (!request.isValid) {
                    throw JakeThrowable(
                        message = request.firstErrorMessage,
                        detail = request.allErrorMessage
                    )
                }
                val requestedUserRole = Role.getRoleOrNull(request.role) ?: kotlin.run {
                    throw JakeThrowable("No such user role")
                }
                val targetUser = userDataSource.getUserById(targetId) ?: kotlin.run {
                    throw JakeThrowable("No such user")
                }
                if (userRole lt Role.Admin && requestedUserRole ge Role.Manager) {
                    throw JakeThrowable("Your access doesn't allow to change manager role")
                }

                val updatedUser = targetUser.copy(
                    role = requestedUserRole
                )

                val wasAcknowledge = userDataSource.updateUser(updatedUser)

                if (!wasAcknowledge) {
                    throw JakeThrowable("Failed to change user role")
                }

                call.respond(
                    BaseResponse.success<Nothing>()
                )
            }
        }
    }
}