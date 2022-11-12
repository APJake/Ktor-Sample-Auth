package com.apjake.routes.user

import com.apjake.data.author.AuthorDataSource
import com.apjake.data.responses.BaseResponse
import com.apjake.mapper.AuthorMapper
import com.apjake.plugins.pipelines.Role
import com.apjake.plugins.pipelines.withAnyRole
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.getAllAuthors() {
    val authorDataSource by inject<AuthorDataSource>()
    authenticate {
        withAnyRole(*Role.courseCreatorRoles) {
            get {
                val query = call.request.queryParameters["q"] ?: ""
                val page = call.request.queryParameters["page"]?.toInt() ?: 0
                val limit = call.request.queryParameters["limit"]?.toInt() ?: 10

                val authors = authorDataSource.getAllAuthors(
                    query,
                    page,
                    limit
                )

                call.respond(
                    BaseResponse.success(
                        authors.mapNotNull { user ->
                            user.author?.let {
                                AuthorMapper.getResponse(it)
                            }
                        }
                    )
                )
            }
        }
    }
}