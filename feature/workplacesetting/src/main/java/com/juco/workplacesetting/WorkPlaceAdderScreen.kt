package com.juco.workplacesetting

import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.juco.feature.workplacesetting.R
import com.juco.workplacesetting.component.CustomCalendarDialog
import com.juco.workplacesetting.model.WorkDayType
import java.time.LocalDate

@Composable
fun WorkPlaceAdderRoute(
    padding: PaddingValues = PaddingValues(),
    viewModel: WorkPlaceViewModel = hiltViewModel()
) {
    val workPlaceName by viewModel.workPlaceName.collectAsStateWithLifecycle()
    val wage by viewModel.wage.collectAsStateWithLifecycle()
    val workDays by viewModel.selectedWorkDays.collectAsStateWithLifecycle()
    val selectedType by viewModel.selectedWorkDayType.collectAsStateWithLifecycle()

    WorkPlaceAdderScreen(
        padding = padding,
        workPlaceName = workPlaceName,
        onWorkPlaceNameChange = { viewModel.workPlaceName.value = it },
        wage = wage,
        onWageChange = { viewModel.wage.value = it },
        selectedWorkDayType = selectedType,
        selectedWorkDays = workDays,
        onWorkDaysSelected = { workDayType ->
            viewModel.selectedWorkDayType.value = workDayType
            viewModel.setWorkDays(workDayType.dayOfWeeks)
        },
        onCustomWorkDaysSelected = { dates ->
            viewModel.setCustomWorkDays(dates)
        },
        onSaveClick = { viewModel.saveWorkPlace() }
    )
}

@Composable
fun WorkPlaceAdderScreen(
    padding: PaddingValues,
    workPlaceName: String,
    onWorkPlaceNameChange: (String) -> Unit,
    wage: String,
    onWageChange: (String) -> Unit,
    selectedWorkDayType: WorkDayType?,
    selectedWorkDays: List<LocalDate>,
    onWorkDaysSelected: (WorkDayType) -> Unit,
    onCustomWorkDaysSelected: (List<LocalDate>) -> Unit,
    onSaveClick: () -> Unit
) {
    var showWorkDayDialog by remember { mutableStateOf(false) }

    val workDaysSummary = remember(selectedWorkDays, selectedWorkDayType) {
        if (selectedWorkDayType == WorkDayType.CUSTOM && selectedWorkDays.isNotEmpty()) {
            val firstDay = selectedWorkDays.first()
            val count = selectedWorkDays.size - 1
            val formattedFirstDay = "${firstDay.monthValue}월 ${firstDay.dayOfMonth}일"
            if (count > 0) "$formattedFirstDay 외 ${count}개" else formattedFirstDay
        } else {
            selectedWorkDayType?.displayName ?: "설정 안됨"
        }
    }

    Column(Modifier.padding(padding).fillMaxSize()) {
        Text(
            text = "근무지 추가",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )

        Column(Modifier.padding(16.dp)) {
            Text("근무지 명", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            TextField(
                text = workPlaceName,
                onValueChange = onWorkPlaceNameChange,
                placeholder = "근무지명을 입력하세요"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("시급", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            TextField(
                text = wage,
                onValueChange = onWageChange,
                placeholder = "시급을 입력하세요",
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showWorkDayDialog = true }
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "일하는 날짜 설정",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = workDaysSummary, color = Color.Gray, fontSize = 18.sp)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dropdown_24dp),
                        contentDescription = "일하는 날짜 설정",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            if (showWorkDayDialog) {
                WorkDaySelectionDialog(
                    initialSelectedDates = selectedWorkDays,
                    onDismiss = { showWorkDayDialog = false },
                    onSelect = { workDayType ->
                        onWorkDaysSelected(workDayType)
                        showWorkDayDialog = false
                    },
                    onCustomWorkDaysSelected = { dates ->
                        onCustomWorkDaysSelected(dates)
                        showWorkDayDialog = false
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSaveClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("저장")
            }
        }
    }
}

@Composable
fun TextField(
    text: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            textStyle = TextStyle(fontSize = 16.sp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (text.isEmpty()) {
                        Text(placeholder, color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )
    }
}

@Composable
fun WorkDaySelectionDialog(
    initialSelectedDates: List<LocalDate>,
    onDismiss: () -> Unit,
    onSelect: (WorkDayType) -> Unit,
    onCustomWorkDaysSelected: (List<LocalDate>) -> Unit
) {
    var showCalendarDialog by remember { mutableStateOf(false) }

    if (showCalendarDialog) {
        CustomCalendarDialog(
            initialSelectedDates = initialSelectedDates,
            onDismiss = { showCalendarDialog = false },
            onConfirm = { selectedDates ->
                onCustomWorkDaysSelected(selectedDates)
                showCalendarDialog = false
                onDismiss()
            }
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("일하는 날짜 선택") },
        text = {
            Column {
                WorkDayOption(
                    text = WorkDayType.WEEKDAYS.displayName,
                    onClick = {
                        onSelect(WorkDayType.WEEKDAYS)
                        onDismiss()
                    }
                )
                WorkDayOption(
                    text = WorkDayType.WEEKENDS.displayName,
                    onClick = {
                        onSelect(WorkDayType.WEEKENDS)
                        onDismiss()
                    }
                )
                WorkDayOption(
                    text = WorkDayType.CUSTOM.displayName,
                    onClick = {
                        showCalendarDialog = true
                    }
                )
            }
        },
        confirmButton = {},
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}

@Composable
fun WorkDayOption(
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(text = text, fontSize = 18.sp)
    }
}