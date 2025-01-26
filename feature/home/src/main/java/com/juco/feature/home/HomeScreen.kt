package com.juco.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeRoute(
    padding: PaddingValues,
    onShowSnackBar: (String) -> Unit
) {
    HomeScreen(
        padding = padding
    )
}

@Composable
fun HomeScreen(
    padding: PaddingValues
) {
    Text(
        modifier = Modifier.padding(padding),
        text = "Home"
    )
}