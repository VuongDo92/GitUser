package com.tymex.gituser.domain

import com.tymex.core.domain.git_user.GitUser
import com.tymex.core.domain.git_user.GitUserName
import com.tymex.core.domain.util.DataError
import com.tymex.core.domain.util.EmptyResult
import com.tymex.core.domain.util.Error
import com.tymex.core.domain.util.Result
import kotlinx.coroutines.flow.Flow


interface GitUserRepository {

    fun getGitUsers(): Flow<List<GitUser>>

    suspend fun fetchGitUsers(
        currentPage: Int,
        pageSize: Int,
    ): Result<List<GitUser>, Error>

    suspend fun fetchGitUserByGitUserName(gitUserName: GitUserName): Result<GitUser, Error>

    suspend fun upsertGitUser(gitUser: GitUser): EmptyResult<DataError>
}
