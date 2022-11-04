package com.apjake.data.requests

import com.apjake.utils.base.BaseRequest
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val username: String,
    val password: String
): BaseRequest() {
    override val validator = {
        if(username.isBlank() || password.isBlank())
            invalid("Username & password must not be empty")
        if(username.length<4)
            invalid("Username must be greater than 4")
        if(password.length<6)
            invalid("Password length must be at least 6")
    }
}