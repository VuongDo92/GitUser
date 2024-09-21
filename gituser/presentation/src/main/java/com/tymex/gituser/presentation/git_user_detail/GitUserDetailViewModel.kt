@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")

package com.tymex.gituser.presentation.git_user_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tymex.core.domain.util.Result
import com.tymex.core.presentation.ui.UiText
import com.tymex.gituser.domain.GitUserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class GitUserDetailViewModel(
    private val gitUserLogin: String,
    private val gitUserRepository: GitUserRepository,
) : ViewModel() {

    var state by mutableStateOf(GitUserDetailState())
        private set

    private val evenChannel = Channel<GitUserDetailEvent>()

    val events = evenChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            gitUserLogin.let { loginName ->
                state = state.copy(
                    isLoading = true
                )
                val gitUser = gitUserRepository.fetchGitUserByGitUserName(loginName)
                if(gitUser is Result.Error) {
                    evenChannel.send(GitUserDetailEvent.Error(UiText.DynamicString(
                        value = "Unknown Error"
                    )))
                    state = state.copy(
                        isLoading = false
                    )
                }
                if(gitUser is Result.Success) {
                    state = state.copy(
                        gitUser = (gitUser as Result.Success).data,
                        isLoading = false,
                        followerNumber = gitUser.data.following ?: 0L,
                        followingNumber = gitUser.data.followers ?: 0L
                    )
                }
            }
        }
    }

    fun onAction(action: GitUserDetailAction) {
        when (action) {

            else -> {

            }
        }
    }
}