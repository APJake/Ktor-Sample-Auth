package com.apjake.data.author

import com.apjake.data.user.User

interface AuthorDataSource {
    suspend fun makeAuthor(user: User, author: Author): Boolean
    suspend fun removeAuthor(user: User): Boolean
}