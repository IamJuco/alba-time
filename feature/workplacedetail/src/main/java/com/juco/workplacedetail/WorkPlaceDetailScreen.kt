package com.juco.workplacedetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.juco.common.LightBlue
import com.juco.common.TitleText
import com.juco.common.Vanilla
import com.juco.common.WageCalculator
import com.juco.common.formatWithComma
import com.juco.domain.model.WorkPlace
import java.time.YearMonth

@Composable
fun WorkPlaceDetailRoute(
    padding: PaddingValues = PaddingValues(),
    popBackStack: () -> Unit,
    viewModel: WorkPlaceDetailViewModel = hiltViewModel(),
    workPlaceDetailId: Int,
    navigateToWorkPlaceEdit: (Int) -> Unit
) {
    val workPlace by viewModel.workPlace.collectAsStateWithLifecycle()

    LaunchedEffect(workPlaceDetailId) {
        viewModel.loadWorkPlaceById(workPlaceDetailId)
    }

    WorkPlaceDetailScreen(
        padding = padding,
        workPlace = workPlace,
        onEditWorkPlace = { navigateToWorkPlaceEdit(workPlaceDetailId) },
        popBackStack = popBackStack
    )
}

@Composable
fun WorkPlaceDetailScreen(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    workPlace: WorkPlace?,
    onEditWorkPlace: (WorkPlace) -> Unit
) {
    var selectedYearMonth by remember { mutableStateOf(YearMonth.now()) }

    val monthlySalaryResult = remember(workPlace, selectedYearMonth) {
        workPlace?.let {
            WageCalculator.calculateMonthlyWageWithAllowance(
                wage = it.wage,
                startTime = it.workTime.workStartTime,
                endTime = it.workTime.workEndTime,
                breakTime = it.breakTime,
                workDays = it.workDays,
                isWeeklyHolidayAllowance = it.isWeeklyHolidayAllowance,
                yearMonth = selectedYearMonth,
                taxRate = it.tax
            )
        } ?: WageCalculator.MonthlyWageResult(0L, 0L, 0L)
    }

    val monthlyTotalBaseSalary = monthlySalaryResult.totalBaseSalary
    val monthlyWeeklyAllowance = remember(workPlace, selectedYearMonth) {
        workPlace?.let {
            WageCalculator.calculateMonthlyWeeklyAllowance(
                wage = it.wage,
                startTime = it.workTime.workStartTime,
                endTime = it.workTime.workEndTime,
                breakTime = it.breakTime,
                workDays = it.workDays,
                yearMonth = selectedYearMonth
            )
        } ?: 0L
    }

    val taxAmount = monthlySalaryResult.totalTaxAmount
    val monthlyWageWithAllowance = monthlySalaryResult.totalSalary

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
                text = "근무지 상세 정보",
                modifier = Modifier.align(Alignment.Center)
            )
        }

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
                    text = workPlace?.name ?: "ERROR",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { selectedYearMonth = selectedYearMonth.minusMonths(1) }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "이전 달")
            }
            Text(
                text = "${selectedYearMonth.year}년 ${selectedYearMonth.monthValue}월",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            IconButton(onClick = { selectedYearMonth = selectedYearMonth.plusMonths(1) }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "다음 달")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        InfoText(title = "시급", value = "${formatWithComma(workPlace?.wage ?: 0)}원")

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${selectedYearMonth.year}년 ${selectedYearMonth.monthValue}월 총 급여",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Vanilla),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${formatWithComma(monthlyWageWithAllowance)}원",
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
                InfoText(title = "기본 급여", value = "${formatWithComma(monthlyTotalBaseSalary)}원")
                InfoText(
                    title = "주휴수당", value = if (workPlace?.isWeeklyHolidayAllowance == false) {
                        "설정 안됨"
                    } else {
                        "+${formatWithComma(monthlyWeeklyAllowance)}원"
                    }
                )
                InfoText(
                    title = "세금 (${workPlace?.tax ?: 0.0f}%)",
                    value = "-${formatWithComma(taxAmount)}원"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { workPlace?.let { onEditWorkPlace(it) } },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
            border = BorderStroke(1.dp, LightBlue)
        ) {
            Text("근무지 수정", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = LightBlue)
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