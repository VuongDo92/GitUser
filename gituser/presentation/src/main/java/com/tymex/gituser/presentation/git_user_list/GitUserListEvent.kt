package com.tymex.gituser.presentation.git_user_list

import com.tymex.core.presentation.ui.UiText

sealed interface GitUserListEvent {

    data class Error(val error: UiText) : GitUserListEvent
}