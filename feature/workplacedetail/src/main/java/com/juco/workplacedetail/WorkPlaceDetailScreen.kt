package com.juco.workplacedetail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WorkPlaceDetailRoute(
    padding: PaddingValues = PaddingValues(),
    workPlaceDetailId: Int
) {
    WorkPlaceDetailScreen(
        padding = padding
    )
}

@Composable
fun WorkPlaceDetailScreen(
    padding: PaddingValues
) {
    Text(
        modifier = Modifier.padding(padding),
        text = "test"
    )
}