package com.tymex.gituser.presentation.git_user_detail

sealed interface GitUserDetailAction {
    data object OnBackClick : GitUserDetailAction

}