package com.juco.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.juco.common.LightBlue
import com.juco.domain.model.WorkPlace
import com.juco.feature.home.component.WorkPlaceCard

@Composable
fun HomeRoute(
    padding: PaddingValues,
    onShowSnackBar: (String) -> Unit,
    navigateToWorkPlaceAdder: () -> Unit,
    navigateToWorkPlaceDetail: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val workPlaces by viewModel.workPlaces.collectAsStateWithLifecycle()

    HomeScreen(
        padding = padding,
        workPlaces = workPlaces,
        navigateToWorkPlaceAdder = navigateToWorkPlaceAdder,
        navigateToWorkPlaceDetail = navigateToWorkPlaceDetail
    )
}

@Composable
fun HomeScreen(
    padding: PaddingValues,
    workPlaces: List<WorkPlace>,
    navigateToWorkPlaceAdder: () -> Unit,
    navigateToWorkPlaceDetail: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(workPlaces) { workPlace ->
            WorkPlaceCard(
                workPlace = workPlace,
                onClick = { navigateToWorkPlaceDetail(workPlace.id) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .height(180.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = LightBlue),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                onClick = navigateToWorkPlaceAdder
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "여기를 눌러 근무지를 추가해주세요!",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.ic_plus_24dp),
                        contentDescription = "근무지 추가하기 아이콘",
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}