package com.tymex.gituser.presentation.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.tymex.core.presentation.designsystem.TymexTheme
import java.util.regex.Pattern


@Composable
fun UriText(
    modifier: Modifier = Modifier,
    text: String
) {
    val context = LocalContext.current

    // Regex to detect URLs
    val urlPattern = Pattern.compile("(https?://[\\w-]+(\\.[\\w-]+)+(/[#&%\\w-?=]+)?)")
    val matcher = urlPattern.matcher(text)

    // Annotated string builder
    val annotatedString = buildAnnotatedString {
        var lastIndex = 0

        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()

            // Add the text before the URL
            append(text.substring(lastIndex, start))

            // Annotate the URL
            pushStringAnnotation(tag = "URL", annotation = text.substring(start, end))
            withStyle(style = androidx.compose.ui.text.SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )) {
                append(text.substring(start, end))
            }
            pop()

            lastIndex = end
        }

        // Add remaining text
        if (lastIndex < text.length) {
            append(text.substring(lastIndex))
        }
    }

    // ClickableText to handle URL clicks
    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    val url = annotation.item
                    // Open URL in browser
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
        }
    )

}

@Preview
@Composable
private fun UriTextPreview() {
    TymexTheme {
        UriText(
            text = "Hello https://api.github.com/users?since=2&per_page=10"
        )
    }
}