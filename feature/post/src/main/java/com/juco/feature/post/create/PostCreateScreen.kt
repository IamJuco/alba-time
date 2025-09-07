package com.juco.feature.post.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.juco.designsystem.theme.AlbaTimeTheme
import com.juco.designsystem.topbar.PreviousTopBar

@Composable
fun PostCreateRoute(
    padding: PaddingValues,
    popBackStack: () -> Unit
) {
    PostCreateScreen(
        padding = padding,
        popBackStack = popBackStack
    )
}

@Composable
fun PostCreateScreen(
    padding: PaddingValues,
    popBackStack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(MaterialTheme.colorScheme.background)
    ) {
        PreviousTopBar(
            title = "게시글 쓰기",
            onPopBackStack = popBackStack
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun PostCreateScreenPreview() {
    AlbaTimeTheme {
        PostCreateScreen(
            padding = PaddingValues(),
            popBackStack = {}
        )
    }
}