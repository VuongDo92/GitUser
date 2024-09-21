package com.tymex.gituser.presentation.git_user_detail

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tymex.core.domain.git_user.GitUser
import com.tymex.core.presentation.designsystem.TymexTheme
import com.tymex.core.presentation.designsystem.components.TymexScaffold
import com.tymex.core.presentation.designsystem.components.TymexToolbar
import com.tymex.core.presentation.ui.ObserveAsEvents
import com.tymex.gituser.presentation.R
import com.tymex.gituser.presentation.components.GitUserCard
import com.tymex.gituser.presentation.components.UriText
import com.tymex.gituser.presentation.git_user_detail.components.FollowNumberCard
import com.tymex.gituser.presentation.git_user_detail.utils.FollowType
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun GitUserDetailScreenRoot( //  koinViewModel(parameters = { parametersOf(gitUserLogin) })
    gitUserLogin: String,
    onBack: () -> Unit,
    viewModel: GitUserDetailViewModel =  koinViewModel(parameters = { parametersOf(gitUserLogin) }),
) {
    val context = LocalContext.current
    ObserveAsEvents(flow = viewModel.events) { event->
        when(event) {
            is GitUserDetailEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> Unit
        }
    }
    GitUserDetailScreen(
        state = viewModel.state.copy(
            gitUserLogin = gitUserLogin
        ),
        onAction = { action ->
            when (action) {
                GitUserDetailAction.OnBackClick -> {
                    onBack()
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GitUserDetailScreen(
    state: GitUserDetailState,
    onAction: (GitUserDetailAction) -> Unit
) {
    TymexScaffold(
        topAppBar = {
            TymexToolbar(
                showBackButton = true,
                title = stringResource(id = R.string.user_detail),
                onBackClick = {
                    onAction(GitUserDetailAction.OnBackClick)
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Column {
                GitUserCard(
                    gitUser = state.gitUser ?: GitUser(id = 1L, login = "Harry"),
                    onItemClick = {}
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FollowNumberCard(followNumber = state.followerNumber, followType = FollowType.FOLLOWER)
                    FollowNumberCard(followNumber = state.followingNumber, followType = FollowType.FOLLOWING)
                }
                Text(
                    text = stringResource(id = R.string.blog),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                state.gitUser?.blog?.let {
                    UriText(text = it)
                }
            }
        }
    }
}

@Preview
@Composable
private fun GitUserDetailScreenPreview() {
    TymexTheme {
        GitUserDetailScreen(
            state = GitUserDetailState(
                gitUser = GitUser(
                    id = 1L,
                    login = "Harry"
                )
            ),
            onAction = {}
        )
    }
}