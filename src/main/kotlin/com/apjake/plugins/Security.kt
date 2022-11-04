package com.apjake.plugins

import com.apjake.security.token.TokenConfig
import com.apjake.utils.AppConstants
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {
    val config: TokenConfig by inject()
    authentication {
        jwt {
            realm = this@configureSecurity.environment.config.property("jwt.realm").getString()
            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.secret))
                    .withAudience(config.audience)
                    .withIssuer(config.issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(config.audience)) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }
}

val JWTPrincipal.userId: String?
    get() = this.getClaim(AppConstants.TOKEN_KEY_USER_ID, String::class)

val JWTPrincipal.userRole: String?
    get() = this.getClaim(AppConstants.TOKEN_KEY_USER_ROLE, String::class)