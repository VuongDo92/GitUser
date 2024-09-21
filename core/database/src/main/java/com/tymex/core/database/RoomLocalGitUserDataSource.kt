package com.tymex.core.database

import android.database.sqlite.SQLiteFullException
import com.tymex.core.database.dao.GitUserDao
import com.tymex.core.database.mappers.toGitUser
import com.tymex.core.database.mappers.toGitUserEntity
import com.tymex.core.domain.git_user.GitUser
import com.tymex.core.domain.git_user.GitUserLocalId
import com.tymex.core.domain.git_user.GitUserName
import com.tymex.core.domain.git_user.GitUserRemoteId
import com.tymex.core.domain.git_user.LocalGitUserDataSource
import com.tymex.core.domain.util.DataError
import com.tymex.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalGitUserDataSource(
    private val gitUserDao: GitUserDao
): LocalGitUserDataSource {

    override fun getGitUsers(): Flow<List<GitUser>> {
        return gitUserDao.getGitUsers()
            .map { runEntities ->
                runEntities.map { it.toGitUser() }
            }
    }

    override fun getGitUserByLocalId(localId: GitUserLocalId): Flow<GitUser> {
        return gitUserDao.getGitUserByLocalId(localId)
            .map {
                it.toGitUser()
            }
    }

    override fun getGitUserByRemoteId(remoteId: GitUserRemoteId): Flow<GitUser> {
        return gitUserDao.getGitUserByRemoteId(remoteId)
            .map {
                it.toGitUser()
            }
    }

    override fun getGitUserByGitUserName(gitUserName: GitUserName): Flow<GitUser> {
        return gitUserDao.getGitUserByGitUserName(gitUserName)
            .map {
                it.toGitUser()
            }
    }

    override suspend fun upsertGitUser(gitUser: GitUser): Result<GitUserLocalId, DataError.Local> {
        return try {
            val entity = gitUser.toGitUserEntity()
            gitUserDao.upsertGitUser(entity)
            Result.Success(entity.localId)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertGitUsers(gitUsers: List<GitUser>): Result<List<GitUserLocalId>, DataError.Local> {
        return try {
            val entities = gitUsers.map { it.toGitUserEntity() }
            gitUserDao.upsertGitUsers(entities)
            Result.Success(entities.map { it.localId })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }
}