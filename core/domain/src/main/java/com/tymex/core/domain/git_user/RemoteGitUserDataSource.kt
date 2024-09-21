package com.tymex.core.domain.git_user

import com.tymex.core.domain.util.Constants
import com.tymex.core.domain.util.DataError
import com.tymex.core.domain.util.Result

interface RemoteGitUserDataSource {

    suspend fun getGitUserByGitUserName(gitUserName: GitUserName): Result<GitUser, DataError.Network>

    suspend fun getGitUsers(
        currentPage: Int,
        pageSize: Int): Result<List<GitUser>, DataError.Network>
}
