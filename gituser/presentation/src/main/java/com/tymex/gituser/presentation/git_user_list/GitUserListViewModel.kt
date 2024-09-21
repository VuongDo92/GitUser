@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")

package com.tymex.gituser.presentation.git_user_list

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tymex.core.domain.util.Constants
import com.tymex.core.domain.util.Result
import com.tymex.core.presentation.ui.UiText
import com.tymex.gituser.domain.GitUserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class GitUserListViewModel(
    private val gitUserRepository: GitUserRepository
) : ViewModel() {

    var state by mutableStateOf(GitUserListState())
        private set

    private val evenChannel = Channel<GitUserListEvent>()
    val events = evenChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            state = state.copy(
                currentPage = 0,
                isLoading = true,
                isRefreshing = true,
                canLoadMore = false
            )
            val gitusers = gitUserRepository.fetchGitUsers(
                currentPage = state.currentPage,
                pageSize = Constants.DEFAULT_PAGE_SIZE
            )
            if(gitusers is Result.Success) {
                state = state.copy(
                    dataList = (gitusers as Result.Success).data,
                    isLoading = false,
                    isRefreshing = false,
                    canLoadMore = gitusers.data.size >= Constants.DEFAULT_PAGE_SIZE
                )
            }
        }
    }

    @SuppressLint("BuildListAdds")
    fun onAction(action: GitUserListAction) {
        when (action) {
            GitUserListAction.OnRefresh -> {
                state = state.copy(
                    currentPage = 0,
                    isLoading = true,
                    isRefreshing = true,
                    canLoadMore = false
                )
                viewModelScope.launch {
                    val gitusers = gitUserRepository.fetchGitUsers(
                        currentPage = state.currentPage,
                        pageSize = Constants.DEFAULT_PAGE_SIZE)
                    if(gitusers is Result.Success) {
                        state = state.copy(
                            dataList = (gitusers as Result.Success).data,
                            isLoading = false,
                            isRefreshing = false,
                            canLoadMore = gitusers.data.size >= Constants.DEFAULT_PAGE_SIZE
                        )
                    }
                }
            }
            GitUserListAction.OnLoadMore -> {
                if (state.canLoadMore) {
                    state = state.copy(
                        currentPage = state.currentPage + 1,
                        isLoading = true,
                        isRefreshing = false,
                    )
                    viewModelScope.launch {
                        val gitusers = gitUserRepository.fetchGitUsers(
                            currentPage = state.currentPage,
                            pageSize = Constants.DEFAULT_PAGE_SIZE
                        )

                        if(gitusers is Result.Success) {
                            state = state.copy(
                                dataList = state.dataList + gitusers.data,
                                isLoading = false,
                                isRefreshing = false,
                                canLoadMore = gitusers.data.size >= Constants.DEFAULT_PAGE_SIZE
                            )
                        } else if (gitusers is Error) {
                            evenChannel.send(GitUserListEvent.Error(UiText.DynamicString(gitusers.message ?: "Unknown Error")))
                        }
                    }
                }
            }
            else -> {

            }
        }
    }
}