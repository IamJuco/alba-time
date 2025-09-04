package com.juco.feature.community.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.juco.designsystem.theme.AlbaTimeTheme

@Composable
fun HomeTabRoute(
    padding: PaddingValues
) {
    HomeTabScreen(
        padding = padding
    )
}

@Composable
fun HomeTabScreen(
    padding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "홈 화면", style = MaterialTheme.typography.bodyLarge)
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun HomeTabScreenPreview() {
    AlbaTimeTheme {
        HomeTabScreen(
            padding = PaddingValues()
        )
    }
}