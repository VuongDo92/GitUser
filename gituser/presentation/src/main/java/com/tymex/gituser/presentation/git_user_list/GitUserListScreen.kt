package com.tymex.gituser.presentation.git_user_list

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tymex.core.domain.git_user.GitUser
import com.tymex.core.presentation.designsystem.TymexPurple40
import com.tymex.core.presentation.designsystem.TymexTheme
import com.tymex.core.presentation.designsystem.components.TymexToolbar
import com.tymex.core.presentation.ui.ObserveAsEvents
import com.tymex.gituser.presentation.components.GitUserCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun GitUserListScreenRoot(
    viewModel: GitUserListViewModel = koinViewModel(),
    onGitUserItemClick: (GitUser) -> Unit
) {
    val context = LocalContext.current
    ObserveAsEvents(flow = viewModel.events) { event ->
        when(event) {
            is GitUserListEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> Unit
        }
    }
    GitUserListScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is GitUserListAction.OnGitUserListClick -> {
                    onGitUserItemClick(action.gitUser)
                }

                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GitUserListScreen(
    state: GitUserListState,
    onAction: (GitUserListAction) -> Unit
) {
    val lazyListState = rememberLazyListState()
    // observer when reached end of list
    val endOfListReached by remember {
        derivedStateOf {
            lazyListState.isScrolledToEnd()
        }
    }
    // Check if the last visible item is the last item in the list
    LaunchedEffect(endOfListReached) {
        onAction(GitUserListAction.OnLoadMore)
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(state.isRefreshing),
        onRefresh = { onAction(GitUserListAction.OnRefresh) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TymexToolbar(
                showBackButton = false,
                title = "Git User List",
                modifier = Modifier.fillMaxWidth(),
            )
            LazyColumn(
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),

            ) {
                items(
                    state.dataList,

                ) { gUser ->
                    GitUserCard(gitUser = gUser) {
                        onAction(GitUserListAction.OnGitUserListClick(gUser))
                    }
                }
            }
        }
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    color = TymexPurple40,       // Set the color of the progress indicator
                    strokeWidth = 8.dp,       // Set the thickness of the stroke
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

fun LazyListState.isScrolledToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

@Preview
@Composable
private fun GitUserListScreenPreview() {
    TymexTheme {
        GitUserListScreen(
            state = GitUserListState(),
            onAction = {

            }
        )
    }
}