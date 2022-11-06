package com.apjake.data.author

import com.apjake.data.user.User
import com.apjake.plugins.pipelines.Role
import com.apjake.utils.helper.lt
import org.litote.kmongo.coroutine.CoroutineDatabase

class AuthorDataSourceImpl(
    db: CoroutineDatabase
) : AuthorDataSource {

    private val users = db.getCollection<User>()

    override suspend fun makeAuthor(user: User, author: Author): Boolean {
        val updatedUser = user.copy(
            author = author,
            role = getUpdatedRole(user)
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

    private fun getUpdatedRole(user: User): Role{
        if(user.role lt Role.Author) return Role.Author
        return user.role
    }

    override suspend fun updateAuthor(user: User, author: Author): Boolean {
        val updatedUser = user.copy(
            author = author,
            role = getUpdatedRole(user)
        )
        return users.updateOneById(
            updatedUser.id,
            updatedUser
        ).wasAcknowledged()
    }
}