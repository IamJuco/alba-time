package com.juco.workplaceedit

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun WorkPlaceEditRoute(
    padding: PaddingValues = PaddingValues(),
    popBackStack: () -> Unit,
    workPlaceEditId: Int
) {
    WorkPlaceEditScreen(
        padding = padding,
        popBackStack = popBackStack
    )
}

@Composable
fun WorkPlaceEditScreen(
    padding: PaddingValues,
    popBackStack: () -> Unit
) {
    Text(
        text = "WorkplaceEditScreen"
    )
}