package com.juco.workplacedetail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun WorkPlaceDetailRoute(
    padding: PaddingValues = PaddingValues(),
    viewModel: WorkPlaceDetailViewModel = hiltViewModel(),
    workPlaceDetailId: Int
) {
    val workPlaces by viewModel.workPlace.collectAsStateWithLifecycle()

    LaunchedEffect(workPlaceDetailId) {
        viewModel.loadWorkPlace(workPlaceDetailId)
    }

    WorkPlaceDetailScreen(
        padding = padding,
        workPlaceName = workPlaces?.name ?: ""
    )
}

@Composable
fun WorkPlaceDetailScreen(
    padding: PaddingValues,
    workPlaceName: String
) {
    Text(
        modifier = Modifier.padding(padding),
        text = workPlaceName
    )
}