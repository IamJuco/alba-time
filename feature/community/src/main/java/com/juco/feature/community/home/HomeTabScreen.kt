package com.juco.feature.community.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juco.designsystem.textfield.CategoryText
import com.juco.designsystem.theme.AlbaTimeTheme

@Composable
fun HomeTabRoute(
) {
    HomeTabScreen()
}

@Composable
fun HomeTabScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            CategoryText(
                text = "인기글"
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun HomeTabScreenPreview() {
    AlbaTimeTheme {
        HomeTabScreen()
    }
}