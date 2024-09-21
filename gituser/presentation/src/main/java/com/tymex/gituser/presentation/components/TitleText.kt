package com.tymex.gituser.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tymex.core.presentation.designsystem.LocationIcon
import com.tymex.core.presentation.designsystem.TymexTheme


@Composable
fun TitleText(
    modifier: Modifier = Modifier,
    text: String,
    startIcon: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        startIcon()
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface

        )
    }
}

@Preview
@Composable
private fun TitleTextPreview() {
    TymexTheme {
        TitleText(
            text = "Vietnam",
            startIcon = {
                IconButton(
                    onClick = { },
                ) {
                    Icon(
                        imageVector = LocationIcon,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = ""
                    )
                }
            },
            onClick = {

            }
        )
    }
}