package com.juco.feature.post.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.juco.designsystem.theme.Blue

@Composable
fun Separator(thickness: Dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(thickness)
            .background(Blue)
    )
}