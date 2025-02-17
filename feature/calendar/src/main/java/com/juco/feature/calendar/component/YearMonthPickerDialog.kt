package com.juco.feature.calendar.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 연도와 월 선택 다이얼로그
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
                            isSelected = (year == selectedYear.intValue),
                            onClick = { selectedYear.intValue = year }
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
                            isSelected = (month == selectedMonth.intValue),
                            onClick = { selectedMonth.intValue = month }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(selectedYear.intValue, selectedMonth.intValue) }) {
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