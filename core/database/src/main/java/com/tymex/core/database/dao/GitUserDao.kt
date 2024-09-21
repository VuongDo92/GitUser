package com.tymex.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tymex.core.database.entity.GitUserEntity
import com.tymex.core.domain.git_user.GitUserLocalId
import com.tymex.core.domain.git_user.GitUserName
import com.tymex.core.domain.git_user.GitUserRemoteId

import kotlinx.coroutines.flow.Flow

@Dao
interface GitUserDao {

    @Upsert
    suspend fun upsertGitUser(gitUser: GitUserEntity)

    @Upsert
    suspend fun upsertGitUsers(gitUsers: List<GitUserEntity>)

    @Query("SELECT * FROM gituserentity")
    fun getGitUsers(): Flow<List<GitUserEntity>>

    @Query("SELECT * FROM gituserentity WHERE localId=:localId")
    fun getGitUserByLocalId(localId: GitUserLocalId): Flow<GitUserEntity>

    @Query("SELECT * FROM gituserentity WHERE id=:remoteId")
    fun getGitUserByRemoteId(remoteId: GitUserRemoteId): Flow<GitUserEntity>

    @Query("SELECT * FROM gituserentity WHERE login=:gitUserName")
    fun getGitUserByGitUserName(gitUserName :GitUserName): Flow<GitUserEntity>
}