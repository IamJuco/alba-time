package com.juco.workplacesetting

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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.juco.feature.workplacesetting.R
import com.juco.workplacesetting.component.InputTextField
import com.juco.workplacesetting.component.PayDaySelector
import com.juco.workplacesetting.component.WorkDaySelectionDialog
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

    var selectedPayDay by remember { mutableStateOf("월급") }
    var showPayDayDialog by remember { mutableStateOf(false) }

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
            InputTextField(
                text = workPlaceName,
                onValueChange = onWorkPlaceNameChange,
                placeholder = "근무지명을 입력하세요"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("시급", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            InputTextField(
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

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "월급일 설정",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            PayDaySelector(
                selectedSalaryType = selectedPayDay,
                onSalaryTypeChange = { selectedPayDay = it },
                onCustomSelected = { showPayDayDialog = true }
            )

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

            if (showPayDayDialog) {
                AlertDialog(
                    onDismissRequest = { showPayDayDialog = false },
                    title = { Text("직접 설정") },
                    text = {
                        Column {
                            Text("원하는 급여일을 입력해주세요")
                            Spacer(modifier = Modifier.height(8.dp))
                            TextField(
                                value = "",
                                onValueChange = {},
                                placeholder = { Text("예: 매월 15일") }
                            )
                        }
                    },
                    confirmButton = {
                        Button(onClick = { showPayDayDialog = false }) {
                            Text("확인")
                        }
                    },
                    dismissButton = {
                        OutlinedButton(onClick = { showPayDayDialog = false }) {
                            Text("취소")
                        }
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