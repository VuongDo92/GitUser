package com.tymex.gituser.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tymex.core.domain.git_user.GitUser
import com.tymex.core.presentation.designsystem.LocationIcon
import com.tymex.core.presentation.designsystem.TymexTheme
import com.tymex.core.presentation.designsystem.TymexWhite
import com.tymex.core.presentation.designsystem.components.TymexUriImage

@Composable
fun GitUserCard(
    modifier: Modifier = Modifier,
    gitUser: GitUser,
    onItemClick: (GitUser) -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(gitUser)
            },
        colors = CardDefaults.cardColors(containerColor = TymexWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth(0.3f)
                    .aspectRatio(1f)
            ) {
                TymexUriImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(1f),
                    url = gitUser.avatarUrl ?: ""
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(
                        4.dp
                    )
            ) {
                Text(
                    text = gitUser.name ?: gitUser.login,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(4.dp))
                gitUser.htmlUrl?.let { url ->
                    UriText(
                        text = url
                    )
                }
                if(gitUser.location != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    TitleText(
                        modifier = Modifier.fillMaxWidth(),
                        text = gitUser.location!!,
                        startIcon = {
                            Icon(
                                imageVector = LocationIcon,
                                contentDescription = ""
                            )
                        },
                        onClick = {

                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun GitUserCardPreview() {
    TymexTheme {
        GitUserCard(
            gitUser = GitUser(
                login = "pjhyett",
                avatarUrl = "https://avatars.githubusercontent.com/u/3?v=4",
                id = 1L,
                followers = 1L,
                following = 1L,
                name = "Athur",
                location = "HCM City",
                localId = "0xFIUKOIHG",
                htmlUrl = "https://github.com/pjhyett"
            ),
            onItemClick = {}
        )
    }
}