package com.juco.feature.calendar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CalendarRoute(
    padding: PaddingValues,
    onShowSnackBar: (String) -> Unit
) {
    CalendarScreen(
        padding = padding
    )
}

@Composable
fun CalendarScreen(
    padding: PaddingValues
) {
    Text(
        modifier = Modifier.padding(padding),
        text = "Calendar"
    )
}