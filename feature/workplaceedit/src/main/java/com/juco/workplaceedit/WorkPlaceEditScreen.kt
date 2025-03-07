package com.juco.workplaceedit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.juco.common.LightBlue
import com.juco.common.TitleText
import com.juco.domain.model.WorkPlace
import com.juco.domain.navigation.MainMenuRoute
import com.juco.domain.navigation.RouteModel

@Composable
fun WorkPlaceEditRoute(
    padding: PaddingValues = PaddingValues(),
    popBackStack: () -> Unit,
    popAllBackStack: (RouteModel) -> Unit,
    workPlaceEditId: Int,
    viewModel: WorkPlaceEditViewModel = hiltViewModel()
) {
    val workPlace by viewModel.workPlace.collectAsStateWithLifecycle()
    val deleteEvent by viewModel.deleteEvent.collectAsStateWithLifecycle(null)

    LaunchedEffect(workPlaceEditId) {
        viewModel.loadWorkPlaceById(workPlaceEditId)
    }

    LaunchedEffect(deleteEvent) {
        deleteEvent?.let { popAllBackStack(MainMenuRoute.Home) }
    }

    WorkPlaceEditScreen(
        padding = padding,
        popBackStack = popBackStack,
        workPlace = workPlace,
        onDeleteWorkPlace = { viewModel.deleteWorkPlace(it) }
    )
}

@Composable
fun WorkPlaceEditScreen(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    workPlace: WorkPlace?,
    onDeleteWorkPlace: (WorkPlace) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            IconButton(
                onClick = { popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "뒤로가기",
                    tint = Color.Black,
                )
            }
            TitleText(
                text = "근무지 수정",
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { workPlace?.let { onDeleteWorkPlace(it) } },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
            border = BorderStroke(1.dp, LightBlue)
        ) {
            Text("근무지 삭제", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Red)
        }
    }
}