package com.tymex.gituser.presentation.git_user_detail

import com.tymex.core.presentation.ui.UiText

sealed interface GitUserDetailEvent {

    data class Error(val error: UiText) : GitUserDetailEvent
}