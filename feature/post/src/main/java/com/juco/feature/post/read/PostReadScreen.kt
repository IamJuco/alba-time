package com.juco.feature.post.read

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.juco.designsystem.theme.AlbaTimeTheme
import com.juco.designsystem.topbar.PreviousTopBar

@Composable
fun PostReadRoute(
    padding: PaddingValues,
    popBackStack: () -> Unit
) {
    PostReadScreen(
        padding = padding,
        popBackStack = popBackStack
    )
}

@Composable
fun PostReadScreen(
    padding: PaddingValues,
    popBackStack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        PreviousTopBar(
            title = "",
            onPopBackStack = popBackStack,
            centerEndAction = {
                IconButton(
                    onClick = {  }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "게시판 메뉴보기",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun PostReadScreenPreview() {
    AlbaTimeTheme {
        PostReadScreen(
            padding = PaddingValues(),
            popBackStack = {}
        )
    }
}