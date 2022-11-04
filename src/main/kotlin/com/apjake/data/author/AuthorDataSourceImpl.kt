package com.apjake.data.author

import com.apjake.data.user.User
import com.apjake.plugins.pipelines.Role
import org.litote.kmongo.coroutine.CoroutineDatabase

class AuthorDataSourceImpl(
    db: CoroutineDatabase
) : AuthorDataSource {

    private val users = db.getCollection<User>()

    override suspend fun makeAuthor(user: User, author: Author): Boolean {
        val updatedUser = user.copy(
            author = author,
            role = Role.Author
        )
        return users.updateOneById(
            updatedUser.id,
            updatedUser
        ).wasAcknowledged()
    }

    override suspend fun removeAuthor(user: User): Boolean {
        val updatedUser = user.copy(
            author = null,
            role = Role.Guest
        )
        return users.updateOneById(
            updatedUser.id,
            updatedUser
        ).wasAcknowledged()
    }
}