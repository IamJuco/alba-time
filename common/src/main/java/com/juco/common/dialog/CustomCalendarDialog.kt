package com.juco.common.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CustomCalendarDialog(
    initialSelectedDates: List<LocalDate>,
    onDismiss: () -> Unit,
    onConfirm: (List<LocalDate>) -> Unit
) {
    var selectedDates by remember { mutableStateOf(initialSelectedDates) }

    val minYear = 2010
    val maxYear = 2049
    val totalMonths = (maxYear - minYear + 1) * 12
    val currentDate = LocalDate.now()
    val initialPage = (currentDate.year - minYear) * 12 + (currentDate.monthValue - 1)

    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { totalMonths }
    )

    AlertDialog(
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismiss,
        title = { Text("날짜 선택", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                val currentYearMonth = remember(pagerState.currentPage) {
                    YearMonth.of(minYear, 1).plusMonths(pagerState.currentPage.toLong())
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${currentYearMonth.year}년 ${currentYearMonth.monthValue}월",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.height(350.dp),
                    verticalAlignment = Alignment.Top
                ) { pageIndex ->
                    val yearMonth = YearMonth.of(minYear, 1).plusMonths(pageIndex.toLong())

                    CalendarGrid(
                        year = yearMonth.year,
                        month = yearMonth.monthValue,
                        selectedDates = selectedDates,
                        onDateClick = { date ->
                            selectedDates = if (selectedDates.contains(date)) {
                                selectedDates - date
                            } else {
                                selectedDates + date
                            }
                        }
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(selectedDates) }) {
                Text("확인")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}

@Composable
fun CalendarGrid(
    year: Int,
    month: Int,
    selectedDates: List<LocalDate>,
    onDateClick: (LocalDate) -> Unit
) {
    val daysInMonth = YearMonth.of(year, month).lengthOfMonth()
    val firstDayOfWeek = LocalDate.of(year, month, 1).dayOfWeek.value % 7
    val totalCells = ((firstDayOfWeek + daysInMonth + 6) / 7) * 7

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("일", "월", "화", "수", "목", "금", "토").forEach {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Column {
            for (row in 0 until totalCells step 7) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (col in 0 until 7) {
                        val dayNumber = row + col - firstDayOfWeek + 1
                        if (dayNumber in 1..daysInMonth) {
                            val date = LocalDate.of(year, month, dayNumber)
                            val isSelected = selectedDates.contains(date)

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(4.dp)
                                    .background(
                                        color = if (isSelected) Color(0xFF90CAF9) else Color.LightGray,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable { onDateClick(date) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$dayNumber",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        } else {
                            Spacer(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}