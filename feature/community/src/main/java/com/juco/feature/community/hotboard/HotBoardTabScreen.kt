package com.juco.feature.community.hotboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.juco.designsystem.theme.AlbaTimeTheme

@Composable
fun HotBoardTabRoute() {
    HotBoardTabScreen()
}

@Composable
fun HotBoardTabScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "인기글", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HotBoardTabScreenPreview() {
    AlbaTimeTheme {
        HotBoardTabScreen()
    }
}