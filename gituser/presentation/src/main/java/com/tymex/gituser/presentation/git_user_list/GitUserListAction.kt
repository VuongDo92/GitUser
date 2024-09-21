package com.tymex.gituser.presentation.git_user_list

import com.tymex.core.domain.git_user.GitUser

sealed interface GitUserListAction {
    data class OnGitUserListClick(val gitUser: GitUser) : GitUserListAction
    data object OnLoadMore : GitUserListAction
    data object OnRefresh : GitUserListAction
}