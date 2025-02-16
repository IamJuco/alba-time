package com.juco.feature.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.fj.koreanlunarcalendar.KoreanLunarCalendarUtils
import com.github.fj.koreanlunarcalendar.KoreanLunarDate
import com.juco.domain.model.WorkPlace
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarRoute(
    padding: PaddingValues,
    onShowSnackBar: (String) -> Unit,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val monthlyWorkPlaces by viewModel.monthlyWorkPlaces.collectAsStateWithLifecycle()

    CalendarScreen(
        padding = padding,
        monthlyWorkPlaces = monthlyWorkPlaces,
        onSelectYearMonth = { year, month ->
            viewModel.loadWorkPlacesForMonth(year, month)
        }
    )
}

@Composable
fun CalendarScreen(
    padding: PaddingValues,
    monthlyWorkPlaces: List<WorkPlace>,
    onSelectYearMonth: (Int, Int) -> Unit
) {
    val currentDate = LocalDate.now()
    val minYear = 2010
    val maxYear = 2049
    val totalMonths = (maxYear - minYear + 1) * 12
    val initialPage = (currentDate.year - minYear) * 12 + (currentDate.monthValue - 1)

    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { totalMonths }
    )

    val coroutineScope = rememberCoroutineScope()
    val showDialog = remember { mutableStateOf(false) }

    val currentYearMonth = remember(pagerState.currentPage) {
        derivedStateOf {
            val offset = pagerState.currentPage
            YearMonth.of(minYear, 1).plusMonths(offset.toLong())
        }
    }

    LaunchedEffect(currentYearMonth.value) {
        onSelectYearMonth(
            currentYearMonth.value.year,
            currentYearMonth.value.monthValue
        )
    }

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        Text(
            text = "${currentYearMonth.value.year}년 ${currentYearMonth.value.monthValue}월",
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .clickable { showDialog.value = true }
        )

        HorizontalPager(
            state = pagerState
        ) { pageIndex ->
            val yearMonth = YearMonth.of(minYear, 1).plusMonths(pageIndex.toLong())

            CalendarView(
                year = yearMonth.year,
                month = yearMonth.monthValue,
                workPlaces = monthlyWorkPlaces
            )
        }

        if (showDialog.value) {
            YearMonthPickerDialog(
                initialYear = currentYearMonth.value.year,
                initialMonth = currentYearMonth.value.monthValue,
                onConfirm = { year, month ->
                    val selectedYearMonth = YearMonth.of(year, month)
                    val baseYearMonth = YearMonth.of(minYear, 1)
                    val monthDifference = java.time.temporal.ChronoUnit.MONTHS.between(
                        baseYearMonth,
                        selectedYearMonth
                    ).toInt()
                    coroutineScope.launch {
                        pagerState.scrollToPage(monthDifference)
                    }
                    showDialog.value = false
                },
                onCancel = { showDialog.value = false }
            )
        }
    }
}

@Composable
fun CalendarView(
    year: Int,
    month: Int,
    workPlaces: List<WorkPlace>
) {
    // 캘린더를 화면크기에 맞게 하기위해 화면크기를 가져옴
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    // 셀의 높이와 너비를 비율로 계산 (요일 + 6주 기준)
    val totalRows = 7 // 1줄 요일 + 6줄 날짜
    val cellHeight = screenHeight / totalRows
    val cellWidth = screenWidth / 7

    val daysInMonth = YearMonth.of(year, month).lengthOfMonth()
    val firstDayOfWeek = LocalDate.of(year, month, 1).dayOfWeek.value % 7 // 0=일요일
    // 셀 개수 계산 ( 날짜가 없는 줄의 요일은 셀을 안쓰기 위해 )
    val totalCells = ((firstDayOfWeek + daysInMonth + 6) / 7) * 7

    // 📌 근무지 정보를 날짜별로 매핑
    val workPlacesByDate = remember(workPlaces) {
        workPlaces.flatMap { workPlace ->
            workPlace.workDays.map { date -> date to workPlace }
        }.groupBy({ it.first }, { it.second })
    }

    // 양력 법정 공휴일
    val solarHolidays = listOf(
        LocalDate.of(year, 1, 1),  // 새해
        LocalDate.of(year, 3, 1), // 3.1절
        LocalDate.of(year, 5, 5), // 어린이날
        LocalDate.of(year, 6, 6), // 현충일
        LocalDate.of(year, 8, 15), // 광복절
        LocalDate.of(year, 10, 3), // 개천절
        LocalDate.of(year, 10, 9), // 한글날
        LocalDate.of(year, 12, 25) // 성탄절
    )
    // 음력 공휴일
    val lunarHolidays = getLunarHolidays(year).mapNotNull { it.toLocalDate() }
    // 대체공휴일
    val substituteHolidays = calculateSubstituteHolidays(solarHolidays + lunarHolidays)
    // 모든 공휴일 병합
    val holidays = (solarHolidays + lunarHolidays + substituteHolidays).toSet()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            listOf("일", "월", "화", "수", "목", "금", "토").forEachIndexed { index, day ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 8.dp, bottom = 8.dp),
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        text = day,
                        fontSize = 16.sp,
                        color = when (index) {
                            0 -> Color.Red // 일요일
                            6 -> Color.Blue // 토요일
                            else -> Color.Black
                        }
                    )
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            items(totalCells) { index ->
                if (index < firstDayOfWeek || index >= firstDayOfWeek + daysInMonth) {
                    // 셀이 없는곳 설정 ( 요일이 없는곳 )
                    Box(
                        modifier = Modifier
                            .size(cellWidth, cellHeight)
                            .drawBehind {
                                drawLine(
                                    color = Color.LightGray,
                                    start = Offset(0f, 0f), // 시작점 (왼쪽 위)
                                    end = Offset(size.width, 0f), // 끝점 (오른쪽 위)
                                )
                            }
                    )
                } else {
                    val day = index - firstDayOfWeek + 1
                    val date = LocalDate.of(year, month, day)
                    val isHoliday = holidays.contains(date)
                    val dayWorkPlaces = workPlacesByDate[date] ?: emptyList()

                    Box(
                        modifier = Modifier
                            .size(cellWidth, cellHeight)
                            .drawBehind {
                                drawLine(
                                    color = Color.LightGray,
                                    start = Offset(0f, 0f), // 시작점 (왼쪽 위)
                                    end = Offset(size.width, 0f), // 끝점 (오른쪽 위)
                                )
                            },
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column(
                            modifier = Modifier.padding(top = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "$day",
                                fontSize = 16.sp,
                                color = when {
                                    isHoliday -> Color.Red
                                    date.dayOfWeek.value == 7 -> Color.Red // 일요일
                                    date.dayOfWeek.value == 6 -> Color.Blue // 토요일
                                    else -> Color.Black
                                }
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            if (dayWorkPlaces.isNotEmpty()) {
                                WorkPlaceCard(dayWorkPlaces)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WorkPlaceCard(workPlaces: List<WorkPlace>) {
    val displayText = when {
        workPlaces.size == 1 -> workPlaces.first().name
        else -> "${workPlaces.first().name} 외 ${workPlaces.size - 1}개"
    }

    Box(
        modifier = Modifier
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = displayText,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray
        )
    }
}

// 대체공휴일 계산 함수
fun calculateSubstituteHolidays(holidays: List<LocalDate>): List<LocalDate> {
    val substituteHolidays = mutableListOf<LocalDate>()
    val existingHolidays = holidays.toMutableSet()

    holidays.forEach { holiday ->
        if (holiday.dayOfWeek.value == 6) { // 토요일
            var substitute = holiday.plusDays(2) // 다음 월요일
            while (existingHolidays.contains(substitute)) {
                substitute = substitute.plusDays(1) // 공휴일과 겹치지 않는 월요일
            }
            substituteHolidays.add(substitute)
            existingHolidays.add(substitute) // 추가된 대체공휴일도 체크
        } else if (holiday.dayOfWeek.value == 7) { // 일요일
            var substitute = holiday.plusDays(1) // 다음 월요일
            while (existingHolidays.contains(substitute)) {
                substitute = substitute.plusDays(1) // 공휴일과 겹치지 않는 월요일
            }
            substituteHolidays.add(substitute)
            existingHolidays.add(substitute) // 추가된 대체공휴일도 체크
        }
    }
    return substituteHolidays
}

// 음력 공휴일 계산
fun getLunarHolidays(year: Int): List<KoreanLunarDate> {
    val holidays = mutableListOf<KoreanLunarDate>()

    // 설날 전날, 당일, 다음날
    val lastDayOfLunarDecember = KoreanLunarCalendarUtils.getDaysOfLunarMonth(year - 1, 12, false)

    holidays.add(
        KoreanLunarCalendarUtils.getSolarDateOf(
            year - 1, 12, lastDayOfLunarDecember, false // 작년 12월 말일
        )
    )
    holidays.add(
        KoreanLunarCalendarUtils.getSolarDateOf(
            year, 1, 1, false // 음력 1월 1일 (설날)
        )
    )
    holidays.add(
        KoreanLunarCalendarUtils.getSolarDateOf(
            year, 1, 2, false // 음력 1월 2일 (설 다음날)
        )
    )

    // 부처님 오신 날 (음력 4월 8일)
    holidays.add(
        KoreanLunarCalendarUtils.getSolarDateOf(
            year, 4, 8, false
        )
    )

    // 추석 전날, 당일, 다음날
    holidays.add(
        KoreanLunarCalendarUtils.getSolarDateOf(
            year, 8, 14, false // 음력 8월 14일
        )
    )
    holidays.add(
        KoreanLunarCalendarUtils.getSolarDateOf(
            year, 8, 15, false // 음력 8월 15일 (추석)
        )
    )
    holidays.add(
        KoreanLunarCalendarUtils.getSolarDateOf(
            year, 8, 16, false // 음력 8월 16일
        )
    )

    return holidays
}

// KoreanLunarDate를 LocalDate로 변환
fun KoreanLunarDate.toLocalDate(): LocalDate? {
    return try {
        LocalDate.of(this.solYear, this.solMonth, this.solDay)
    } catch (e: Exception) {
        // catch가 아니라 다른걸로 할까?
        null
    }
}

@Composable
fun YearMonthPickerDialog(
    initialYear: Int,
    initialMonth: Int,
    onConfirm: (Int, Int) -> Unit,
    onCancel: () -> Unit
) {
    val selectedYear = remember { mutableIntStateOf(initialYear) }
    val selectedMonth = remember { mutableIntStateOf(initialMonth) }

    val yearListState = rememberLazyListState(initialFirstVisibleItemIndex = initialYear - 2010)
    val monthListState = rememberLazyListState(initialFirstVisibleItemIndex = initialMonth - 1)

    AlertDialog(
        onDismissRequest = { onCancel() },
        title = { Text(text = "날짜 선택", fontSize = 20.sp) },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                LazyColumn(
                    state = yearListState,
                    modifier = Modifier
                        .weight(1f)
                        .height(240.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items((2010..2049).toList()) { year ->
                        YearOrMonthItem(
                            value = "${year}년",
                            isSelected = (year == selectedYear.value),
                            onClick = { selectedYear.value = year }
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                LazyColumn(
                    state = monthListState,
                    modifier = Modifier
                        .weight(1f)
                        .height(240.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items((1..12).toList()) { month ->
                        YearOrMonthItem(
                            value = "${month}월",
                            isSelected = (month == selectedMonth.value),
                            onClick = { selectedMonth.value = month }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(selectedYear.value, selectedMonth.value) }) {
                Text(text = "확인")
            }
        },
        dismissButton = {
            Button(onClick = { onCancel() }) {
                Text(text = "취소")
            }
        }
    )
}

@Composable
fun YearOrMonthItem(
    value: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = value,
        fontSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        textAlign = TextAlign.Center,
        color = if (isSelected) Color.Blue else Color.Black
    )
}