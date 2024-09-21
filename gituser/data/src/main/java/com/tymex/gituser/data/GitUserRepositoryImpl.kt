package com.tymex.gituser.data

import com.tymex.core.domain.git_user.GitUser
import com.tymex.core.domain.git_user.GitUserName
import com.tymex.core.domain.git_user.LocalGitUserDataSource
import com.tymex.core.domain.git_user.RemoteGitUserDataSource
import com.tymex.core.domain.util.DataError
import com.tymex.core.domain.util.EmptyResult
import com.tymex.core.domain.util.Error
import com.tymex.core.domain.util.Result
import com.tymex.core.domain.util.asEmptyDataResult
import com.tymex.core.domain.util.asEmptyListResult
import com.tymex.gituser.domain.GitUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class GitUserRepositoryImpl(
    private val applicationScope: CoroutineScope,
    private val localGitUserDataSource: LocalGitUserDataSource,
    private val remoteGitUserDataSource: RemoteGitUserDataSource
) : GitUserRepository {

    override fun getGitUsers(): Flow<List<GitUser>> {
        return localGitUserDataSource.getGitUsers()
    }

    override suspend fun fetchGitUsers(
        currentPage: Int,
        pageSize: Int
    ): Result<List<GitUser>, Error> {

        return when(val result = remoteGitUserDataSource.getGitUsers(
            currentPage = currentPage,
            pageSize = pageSize
        )) {
            is Result.Error -> result.asEmptyListResult()
            is Result.Success -> {
                applicationScope.async {
                    localGitUserDataSource.upsertGitUsers(result.data).asEmptyDataResult()
                }.await()
                Result.Success<List<GitUser>>(result.data)
            }
        }
    }

    override suspend fun fetchGitUserByGitUserName(gitUserName: GitUserName): Result<GitUser, DataError> {
        return when (val result = remoteGitUserDataSource.getGitUserByGitUserName(gitUserName)) {
            is Result.Error -> {
                Result.Error(DataError.Network.SERVER_ERROR)
            }
            is Result.Success -> {
                applicationScope.async {
                    localGitUserDataSource.upsertGitUser(result.data).asEmptyDataResult()
                }.await()
                Result.Success<GitUser>(result.data)
            }
        }
    }

    override suspend fun upsertGitUser(gitUser: GitUser): EmptyResult<DataError> {
        return applicationScope.async {
            localGitUserDataSource.upsertGitUser(gitUser).asEmptyDataResult()
        }.await()
    }
}