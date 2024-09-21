package com.tymex.gituser.presentation.git_user_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tymex.core.presentation.designsystem.MedalIcon
import com.tymex.core.presentation.designsystem.PeopleIcon
import com.tymex.core.presentation.designsystem.TymexGray40
import com.tymex.core.presentation.designsystem.TymexTheme
import com.tymex.gituser.presentation.git_user_detail.utils.FollowType
import com.tymex.gituser.presentation.utils.Utils


@Composable
fun FollowNumberCard(
    modifier: Modifier = Modifier,
    followNumber: Long,
    followType: FollowType
) {

    Box(
        modifier = Modifier.size(150.dp),
        ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = { },
            ) {
                Icon(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(TymexGray40)
                        .padding(8.dp),
                    imageVector = if (followType == FollowType.FOLLOWING) MedalIcon else PeopleIcon,
                    contentDescription = ""
                )
            }
            Text(text = Utils.getRoundedNumber(followNumber))
            Text(text = if(followType == FollowType.FOLLOWING) "Following" else "Follower",
                color = MaterialTheme.colorScheme.onSurface
            )

        }
    }
}

@Preview
@Composable
private fun FollowNumberCardPreview() {
    TymexTheme {
        FollowNumberCard(
            followNumber = 89L,
            followType = FollowType.FOLLOWER
        )
    }
}