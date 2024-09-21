package com.tymex.gituser.presentation.git_user_detail

import com.tymex.core.domain.git_user.GitUser
import com.tymex.core.domain.git_user.GitUserName

data class GitUserDetailState(
    val gitUser: GitUser? = null,
    val gitUserLogin: GitUserName? = null,
    val followerNumber: Long = 0,
    val followingNumber: Long = 0,
    val isLoading: Boolean = false
)
