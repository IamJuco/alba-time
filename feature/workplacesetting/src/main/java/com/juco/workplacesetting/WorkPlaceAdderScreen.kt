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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.juco.common.TitleText
import com.juco.feature.workplacesetting.R
import com.juco.workplacesetting.component.BreakTimeSelectionDialog
import com.juco.workplacesetting.component.InputNumberField
import com.juco.workplacesetting.component.InputTextField
import com.juco.workplacesetting.component.PayDaySelectionDialog
import com.juco.workplacesetting.component.PayDaySelector
import com.juco.workplacesetting.component.SamsungStyleTimePickerDialog
import com.juco.workplacesetting.component.TaxSelectionDialog
import com.juco.workplacesetting.component.WorkDaySelectionDialog
import com.juco.workplacesetting.component.WorkPlaceCardColorSelectionDialog
import com.juco.workplacesetting.mapper.toLocalTime
import com.juco.workplacesetting.mapper.toTimeString
import com.juco.workplacesetting.model.UiPayDay
import com.juco.workplacesetting.model.UiTaxType
import com.juco.workplacesetting.model.UiWorkTime
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
    val selectedPayDay by viewModel.selectedPayDay.collectAsStateWithLifecycle()
    val workTime by viewModel.workTime.collectAsStateWithLifecycle()
    val breakTime by viewModel.breakTime.collectAsStateWithLifecycle()
    val selectedWorkPlaceCardColor by viewModel.selectedWorkPlaceCardColor.collectAsStateWithLifecycle()
    val isWeeklyHolidayAllowance by viewModel.isWeeklyHolidayAllowance.collectAsStateWithLifecycle()
    val tax by viewModel.selectedTax.collectAsStateWithLifecycle()

    WorkPlaceAdderScreen(
        padding = padding,
        workPlaceName = workPlaceName,
        onWorkPlaceNameChange = { viewModel.workPlaceName.value = it },
        wage = wage,
        onWageChange = { viewModel.wage.value = it },
        selectedWorkDayType = selectedType,
        selectedWorkDays = workDays,
        selectedPayDay = selectedPayDay,
        onPayDaySelected = { viewModel.setPayDay(it) },
        onWorkDaysSelected = { workDayType ->
            viewModel.selectedWorkDayType.value = workDayType
            viewModel.setWorkDays(workDayType.dayOfWeeks)
        },
        onCustomWorkDaysSelected = { dates ->
            viewModel.setCustomWorkDays(dates)
        },
        workTime = workTime,
        onWorkTimeChange = { viewModel.setWorkTime(it) },
        selectedWorkPlaceCardColor = selectedWorkPlaceCardColor,
        onWorkPlaceCardColorSelected = { viewModel.setWorkPlaceCardColor(it) },
        breakTime = breakTime,
        onBreakTimeChange = { viewModel.setBreakTime(it) },
        isWeeklyHolidayAllowance = isWeeklyHolidayAllowance,
        onWeeklyHolidayAllowanceChange = { viewModel.setWeeklyHolidayAllowanceEnabled(it) },
        selectedTax = tax,
        onTaxSelected = { viewModel.setTax(it) },
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
    selectedPayDay: UiPayDay,
    onPayDaySelected: (UiPayDay) -> Unit,
    onWorkDaysSelected: (WorkDayType) -> Unit,
    onCustomWorkDaysSelected: (List<LocalDate>) -> Unit,
    workTime: UiWorkTime,
    onWorkTimeChange: (UiWorkTime) -> Unit,
    breakTime: String,
    onBreakTimeChange: (String) -> Unit,
    selectedWorkPlaceCardColor: Color,
    isWeeklyHolidayAllowance: Boolean,
    onWeeklyHolidayAllowanceChange: (Boolean) -> Unit,
    onWorkPlaceCardColorSelected: (Color) -> Unit,
    selectedTax: UiTaxType,
    onTaxSelected: (UiTaxType) -> Unit,
    onSaveClick: () -> Unit
) {
    var showWorkDayDialog by remember { mutableStateOf(false) }
    var showPayDayDialog by remember { mutableStateOf(false) }
    var showBreakTimeDialog by remember { mutableStateOf(false) }
    var showWorkPlaceCardColorDialog by remember { mutableStateOf(false) }
    var showTaxDialog by remember { mutableStateOf(false) }

    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

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

    Column(
        Modifier
            .padding(padding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding() // TODO 핸드폰으로 확인해보기
    ) {
        TitleText(
            text = "근무지 추가",
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
            InputNumberField(
                text = wage,
                onValueChange = onWageChange,
                placeholder = "시급을 입력하세요",
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("일하는 시간 설정", fontWeight = FontWeight.Bold, fontSize = 24.sp)

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showStartTimePicker = true }
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = workTime.startTime.toTimeString(),
                        fontSize = 20.sp,
                        color = Color.Gray
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Gray)
                    )
                }

                Text(
                    text = "-",
                    fontSize = 24.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showEndTimePicker = true }
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = workTime.endTime.toTimeString(),
                        fontSize = 20.sp,
                        color = Color.Gray
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Gray)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showBreakTimeDialog = true },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "휴게 시간 설정",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (breakTime.isEmpty() || breakTime == "0") "없음" else "$breakTime 분",
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dropdown_24dp),
                        contentDescription = "휴게 시간 설정",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showWorkDayDialog = true },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "일하는 날짜 설정", fontWeight = FontWeight.Bold, fontSize = 24.sp)
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
                selectedPayDay = selectedPayDay,
                onPayDayChange = { payDay ->
                    onPayDaySelected(payDay)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "주휴수당 설정",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Switch(
                    checked = isWeeklyHolidayAllowance,
                    onCheckedChange = onWeeklyHolidayAllowanceChange
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showTaxDialog = true },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "세금 설정",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = selectedTax.displayName,
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dropdown_24dp),
                        contentDescription = "세금 설정",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showWorkPlaceCardColorDialog = true },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "근무지 카드 색상 설정",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(selectedWorkPlaceCardColor, shape = CircleShape)
                )
            }

            if (showWorkPlaceCardColorDialog) {
                WorkPlaceCardColorSelectionDialog(
                    selectedColor = selectedWorkPlaceCardColor,
                    onDismiss = { showWorkPlaceCardColorDialog = false },
                    onColorSelected = {
                        onWorkPlaceCardColorSelected(it)
                        showWorkPlaceCardColorDialog = false
                    }
                )
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

            if (showPayDayDialog) {
                PayDaySelectionDialog(
                    payDay = selectedPayDay,
                    onDismiss = { showPayDayDialog = false },
                    onConfirm = { updatedPayDay ->
                        onPayDaySelected(updatedPayDay)
                        showPayDayDialog = false
                    }
                )
            }

            if (showStartTimePicker) {
                SamsungStyleTimePickerDialog(
                    initialTime = workTime.startTime.toTimeString(),
                    onDismiss = { showStartTimePicker = false },
                    onConfirm = { selectedTime ->
                        onWorkTimeChange(workTime.copy(startTime = selectedTime.toLocalTime()))
                        showStartTimePicker = false
                    }
                )
            }

            if (showEndTimePicker) {
                SamsungStyleTimePickerDialog(
                    initialTime = workTime.endTime.toTimeString(),
                    onDismiss = { showEndTimePicker = false },
                    onConfirm = { selectedTime ->
                        onWorkTimeChange(workTime.copy(endTime = selectedTime.toLocalTime()))
                        showEndTimePicker = false
                    }
                )
            }

            if (showBreakTimeDialog) {
                BreakTimeSelectionDialog(
                    currentBreakTime = breakTime,
                    onDismiss = { showBreakTimeDialog = false },
                    onBreakTimeSelected = { selectedBreakTime ->
                        onBreakTimeChange(selectedBreakTime)
                        showBreakTimeDialog = false
                    }
                )
            }

            if (showTaxDialog) {
                TaxSelectionDialog(
                    selectedTax = selectedTax,
                    onDismiss = { showTaxDialog = false },
                    onConfirm = {
                        onTaxSelected(it)
                        showTaxDialog = false
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