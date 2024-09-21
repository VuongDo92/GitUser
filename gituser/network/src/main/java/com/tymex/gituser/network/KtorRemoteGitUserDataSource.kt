package com.tymex.gituser.network

import com.tymex.core.data.get
import com.tymex.core.domain.git_user.GitUser
import com.tymex.core.domain.git_user.GitUserName
import com.tymex.core.domain.git_user.RemoteGitUserDataSource
import com.tymex.core.domain.util.Constants
import com.tymex.core.domain.util.DataError
import com.tymex.core.domain.util.Result
import com.tymex.core.domain.util.map
import io.ktor.client.HttpClient

class KtorRemoteGitUserDataSource(
    private val httpClient: HttpClient,
): RemoteGitUserDataSource {

    override suspend fun getGitUsers(
        currentPage: Int,
        pageSize: Int
    ): Result<List<GitUser>, DataError.Network> {
        return httpClient.get<List<GitUserDto>>(
            route = "/users",
            queryParameters = mapOf(
                Constants.CURRENT_PAGE_KEY to currentPage,
                Constants.PAGE_SIZE_KEY to pageSize,
            )
        ).map { dtos ->
            dtos.map { it.toGitUser() }
        }
    }

    override suspend fun getGitUserByGitUserName(gitUserName: GitUserName): Result<GitUser, DataError.Network> {
        return httpClient.get<GitUserDto>(
            route = "/users/$gitUserName",
        ).map { dto ->
            dto.toGitUser()
        }
    }
}