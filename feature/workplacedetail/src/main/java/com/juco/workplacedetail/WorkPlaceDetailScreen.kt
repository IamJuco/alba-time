package com.juco.workplacedetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.juco.domain.model.WorkPlace

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
        workPlace = workPlaces,
        onDeleteWorkPlace = { viewModel.deleteWorkPlace(it) }
    )
}

@Composable
fun WorkPlaceDetailScreen(
    padding: PaddingValues,
    workPlace: WorkPlace?,
    onDeleteWorkPlace: (WorkPlace) -> Unit,
) {
    Text(
        modifier = Modifier.padding(padding).clickable {
            workPlace?.let { onDeleteWorkPlace(it) }
        },
        text = workPlace?.name ?: "",
        fontSize = 50.sp,
    )
}