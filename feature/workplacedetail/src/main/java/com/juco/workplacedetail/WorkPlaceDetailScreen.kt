package com.juco.workplacedetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.juco.common.TitleText
import com.juco.common.formatWithComma
import com.juco.common.monthlyWageTotalCalculator
import com.juco.common.monthlyWageTotalWithAllowanceCalculator
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
                yearMonth = yearMonth,
            )
        } ?: 0L
    }

    val monthlyWageWithAllowance = remember(workPlace, yearMonth) {
        workPlace?.let {
            monthlyWageTotalWithAllowanceCalculator(
                wage = it.wage,
                startTime = it.workTime.workStartTime,
                endTime = it.workTime.workEndTime,
                breakTime = it.breakTime,
                workDays = it.workDays,
                isWeeklyHolidayAllowance = it.isWeeklyHolidayAllowance,
                yearMonth = yearMonth,
                taxRate = it.tax
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

    val taxAmount = ((monthlyWageWithAllowance * (workPlace?.tax ?: 0.0f)) / 100).toLong()
    val totalSalaryCalculation = monthlyWageWithAllowance - taxAmount

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
                .height(100.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(
                    workPlace?.workPlaceCardColor ?: Color.Black.toArgb()
                )
            ),
            elevation = CardDefaults.cardElevation(5.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.clickable { workPlace?.let { onDeleteWorkPlace(it) } },
                    text = workPlace?.name ?: "ERROR",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        InfoText(title = "시급", value = "${formatWithComma(workPlace?.wage ?: 0)}원")

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "이번 달 총 급여",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF3E0)),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${formatWithComma(totalSalaryCalculation)}원",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "총 급여 계산 과정",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                InfoText(title = "이번 달 기본 급여", value = "${formatWithComma(monthlyWage)}원")
                InfoText(title = "주휴수당", value = "+${formatWithComma(weeklyAllowance)}원")
                InfoText(title = "세금 (${workPlace?.tax ?: 0.0f}%)", value = "-${formatWithComma(taxAmount)}원")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* TODO: 근무지 수정 기능 추가 */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A89CC))
        ) {
            Text("근무지 수정", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun InfoText(title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
        Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}