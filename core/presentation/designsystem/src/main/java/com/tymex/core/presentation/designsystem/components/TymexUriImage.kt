package com.tymex.core.presentation.designsystem.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.tymex.core.presentation.designsystem.TymexTheme


@Composable
fun TymexUriImage(
    modifier: Modifier = Modifier,
    url: String
) {
    AsyncImage(
        model = url,
        contentDescription = "Image from URL",
        modifier = modifier
    )

}


@Preview
@Composable
private fun TymexUriImagePreview() {
    TymexTheme {
        TymexUriImage(
            url = "https://avatars.githubusercontent.com/u/3?v=4"
        )
    }
}