package com.tymex.core.domain.git_user

import com.tymex.core.domain.util.DataError
import com.tymex.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

typealias GitUserLocalId = String
typealias GitUserRemoteId = Long
typealias GitUserName = String

interface LocalGitUserDataSource {
    fun getGitUsers(): Flow<List<GitUser>>

    fun getGitUserByLocalId(localId: GitUserLocalId): Flow<GitUser>

    fun getGitUserByRemoteId(remoteId: GitUserRemoteId): Flow<GitUser>

    fun getGitUserByGitUserName(gitUserName: GitUserName): Flow<GitUser>

    suspend fun upsertGitUser(gitUser: GitUser): Result<GitUserLocalId, DataError.Local>

    suspend fun upsertGitUsers(gitUsers: List<GitUser>): Result<List<GitUserLocalId>, DataError.Local>
}