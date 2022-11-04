package com.apjake.di

import com.apjake.data.author.AuthorDataSource
import com.apjake.data.author.AuthorDataSourceImpl
import com.apjake.data.course.CourseDataSource
import com.apjake.data.course.CourseDataSourceImpl
import com.apjake.data.user.UserDataSource
import com.apjake.data.user.UserDataSourceImpl
import com.apjake.plugins.configureDatabase
import com.apjake.security.hashing.HashingService
import com.apjake.security.hashing.SHA256HashingService
import com.apjake.security.token.JwtTokenService
import com.apjake.security.token.TokenConfig
import com.apjake.security.token.TokenService
import io.ktor.server.application.*
import org.koin.dsl.module

fun serviceModule(environment: ApplicationEnvironment) = module {
    single<HashingService> { SHA256HashingService() }
    single<TokenService> { JwtTokenService() }
    single {
        TokenConfig(
            issuer = environment.config.property("jwt.issuer").getString(),
            audience = environment.config.property("jwt.audience").getString(),
            expiresIn = 365L * 1000L * 60L * 60L * 24L,
            secret = System.getenv("JWT_SECRET")
        )
    }
}

val dataSourceModule = module {
    val db = configureDatabase()
    single<UserDataSource> { UserDataSourceImpl(db) }
    single<CourseDataSource> { CourseDataSourceImpl(db) }
    single<AuthorDataSource> { AuthorDataSourceImpl(db) }
}