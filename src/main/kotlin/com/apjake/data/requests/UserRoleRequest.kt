package com.apjake.data.requests

import com.apjake.plugins.pipelines.Role
import com.apjake.utils.base.BaseRequest
import kotlinx.serialization.Serializable

@Serializable
data class UserRoleRequest(
    val role: String
) : BaseRequest() {
    override val validator = {
        if (Role.getRoleOrNull(role) == null)
            invalid("Invalid role")
    }
}