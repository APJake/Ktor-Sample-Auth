package com.apjake.usecase

import com.apjake.data.author.Author
import com.apjake.data.user.UserDataSource
import com.apjake.plugins.userId
import com.apjake.utils.throwable.JakeThrowable
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

object RetrieveAuthor {
    suspend operator fun invoke(
        principal: Principal?,
        userDataSource: UserDataSource
    ): Author {
        val userId = (principal as JWTPrincipal?)?.userId ?: kotlin.run {
            throw JakeThrowable(
                code = HttpStatusCode.Unauthorized.value,
                message = "Please login again",
            )
        }

        return userDataSource.getUserById(userId)?.author ?: kotlin.run {
            throw JakeThrowable(
                code = HttpStatusCode.Unauthorized.value,
                message = "You are not an author!",
            )
        }
    }
}