package com.juco.workplacedetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.juco.common.TitleText
import com.juco.common.formatWithComma
import com.juco.common.monthlyWageTotalCalculator
import com.juco.common.weeklyAllowanceTotalCalculator
import com.juco.domain.model.WorkPlace
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun WorkPlaceDetailRoute(
    padding: PaddingValues = PaddingValues(),
    viewModel: WorkPlaceDetailViewModel = hiltViewModel(),
    workPlaceDetailId: Int
) {
    val workPlace by viewModel.workPlace.collectAsStateWithLifecycle()

    LaunchedEffect(workPlaceDetailId) {
        viewModel.loadWorkPlaceById(workPlaceDetailId)
    }

    WorkPlaceDetailScreen(
        padding = padding,
        workPlace = workPlace,
        onDeleteWorkPlace = { viewModel.deleteWorkPlace(it) }
    )
}

@Composable
fun WorkPlaceDetailScreen(
    padding: PaddingValues,
    workPlace: WorkPlace?,
    onDeleteWorkPlace: (WorkPlace) -> Unit
) {
    val yearMonth = YearMonth.now()
    val currentDate = LocalDate.now()

    val monthlyWage = remember(workPlace, yearMonth) {
        workPlace?.let {
            monthlyWageTotalCalculator(
                wage = it.wage,
                startTime = it.workTime.workStartTime,
                endTime = it.workTime.workEndTime,
                breakTime = it.breakTime,
                workDays = it.workDays,
                isWeeklyHolidayAllowance = it.isWeeklyHolidayAllowance,
                yearMonth = yearMonth
            )
        } ?: 0L
    }

    val weeklyAllowance = remember(workPlace) {
        workPlace?.let {
            weeklyAllowanceTotalCalculator(
                wage = it.wage,
                startTime = it.workTime.workStartTime,
                endTime = it.workTime.workEndTime,
                breakTime = it.breakTime,
                workDays = it.workDays,
                currentDate = currentDate
            )
        } ?: 0L
    }

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(16.dp)
            .fillMaxSize()
    ) {

        TitleText(
            text = "근무지 상세 정보",
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(
                    workPlace?.workPlaceCardColor ?: Color.Black.toArgb()
                )
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.clickable {
                        workPlace?.let { onDeleteWorkPlace(it) }
                    },
                    text = workPlace?.name ?: "ERROR",
                    fontSize = 30.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "시급: ${formatWithComma(workPlace?.wage ?: 0)}원",
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "이번 달 총 급여: ${formatWithComma(monthlyWage)}원",
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "주휴수당: ${formatWithComma(weeklyAllowance)}원",
            fontSize = 30.sp
        )

        Button(
            onClick = { TODO() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("근무지 수정")
        }
    }
}