package com.tymex.gituser.presentation.git_user_list

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import com.tymex.core.domain.git_user.GitUser

data class GitUserListState(
    val currentPage: Int = 0,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val canLoadMore: Boolean = false,
    val reachedBottom: Boolean = false,
    val dataList: List<GitUser> = emptyList()
)
