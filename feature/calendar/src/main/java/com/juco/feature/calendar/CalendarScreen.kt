package com.juco.feature.calendar

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.juco.domain.model.WorkPlace
import com.juco.feature.calendar.component.WorkPlaceCard
import com.juco.feature.calendar.component.YearMonthPickerDialog
import com.juco.feature.calendar.util.lunarHolidays
import com.juco.feature.calendar.util.solarHolidays
import com.juco.feature.calendar.util.substituteHolidaysCalculator
import com.juco.feature.calendar.util.toLocalDate
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
    var showDialog by remember { mutableStateOf(false) }

    val currentYearMonth by remember(pagerState.currentPage) {
        derivedStateOf {
            YearMonth.of(minYear, 1).plusMonths(pagerState.currentPage.toLong())
        }
    }

    LaunchedEffect(currentYearMonth) {
        viewModel.loadWorkPlacesForMonth(
            currentYearMonth.year,
            currentYearMonth.monthValue
        )
    }

    CalendarScreen(
        padding = padding,
        monthlyWorkPlaces = monthlyWorkPlaces,
        currentYearMonth = currentYearMonth,
        pagerState = pagerState,
        showDialog = showDialog,
        onShowDialogChange = { showDialog = it },
        onScrollToMonth = { year, month ->
            val selectedYearMonth = YearMonth.of(year, month)
            val baseYearMonth = YearMonth.of(minYear, 1)
            val monthDifference = java.time.temporal.ChronoUnit.MONTHS.between(
                baseYearMonth, selectedYearMonth
            ).toInt()
            coroutineScope.launch {
                pagerState.scrollToPage(monthDifference)
            }
        }
    )
}

@Composable
fun CalendarScreen(
    padding: PaddingValues,
    monthlyWorkPlaces: List<WorkPlace>,
    currentYearMonth: YearMonth,
    pagerState: PagerState,
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    onScrollToMonth: (Int, Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        // 날짜 헤더
        Text(
            text = "${currentYearMonth.year}년 ${currentYearMonth.monthValue}월",
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .clickable { onShowDialogChange(true) }
        )

        HorizontalPager(
            state = pagerState
        ) { pageIndex ->
            val yearMonth = YearMonth.of(2010, 1).plusMonths(pageIndex.toLong())

            CalendarCell(
                year = yearMonth.year,
                month = yearMonth.monthValue,
                workPlaces = monthlyWorkPlaces
            )
        }

        if (showDialog) {
            YearMonthPickerDialog(
                initialYear = currentYearMonth.year,
                initialMonth = currentYearMonth.monthValue,
                onConfirm = { year, month ->
                    onScrollToMonth(year, month)
                    onShowDialogChange(false)
                },
                onCancel = { onShowDialogChange(false) }
            )
        }
    }
}

@Composable
fun CalendarCell(
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

    // 양력 공휴일
    val solarHolidays = solarHolidays(year)
    // 음력 공휴일
    val lunarHolidays = lunarHolidays(year).mapNotNull { it.toLocalDate() }
    // 대체 공휴일
    val substituteHolidays = substituteHolidaysCalculator(solarHolidays + lunarHolidays)
    // 모든 공휴일
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