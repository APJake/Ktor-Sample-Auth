package com.apjake.data.author

import com.apjake.data.user.User

interface AuthorDataSource {
    suspend fun makeAuthor(user: User, author: Author): Boolean
    suspend fun getUserByAuthorName(authorName: String): User?
    suspend fun updateAuthor(user: User, author: Author): Boolean
    suspend fun removeAuthor(user: User): Boolean
}