package com.juco.workplaceedit

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.juco.common.InputNumberField
import com.juco.common.InputTextField
import com.juco.common.SubtitleText
import com.juco.common.TitleText
import com.juco.common.dialog.BreakTimeSelectionDialog
import com.juco.common.dialog.PayDaySelectionDialog
import com.juco.common.dialog.PayDaySelector
import com.juco.common.dialog.SamsungStyleTimePickerDialog
import com.juco.common.dialog.TaxSelectionDialog
import com.juco.common.dialog.WorkDaySelectionDialog
import com.juco.common.dialog.WorkPlaceCardColorSelectionDialog
import com.juco.common.mapper.toLocalTime
import com.juco.common.mapper.toTimeString
import com.juco.common.model.UiPayDay
import com.juco.common.model.UiTaxType
import com.juco.common.model.UiWorkTime
import com.juco.designsystem.theme.LightBlue
import com.juco.designsystem.theme.Red
import com.juco.domain.model.WorkPlace
import com.juco.domain.navigation.MainMenuRoute
import com.juco.domain.navigation.RouteModel
import com.juco.feature.workplaceedit.R
import com.juco.workplaceedit.component.DeleteWorkPlaceDialog
import com.juco.workplaceedit.mapper.toDomain
import com.juco.workplaceedit.mapper.toUiModel
import com.juco.workplaceedit.util.workDayTypeSetter
import com.juco.workplaceedit.util.getWorkDaysSummary
import java.time.LocalDate

@Composable
fun WorkPlaceEditRoute(
    padding: PaddingValues = PaddingValues(),
    popBackStack: () -> Unit,
    popAllBackStack: (RouteModel) -> Unit,
    workPlaceEditId: Int,
    viewModel: WorkPlaceEditViewModel = hiltViewModel()
) {
    val workPlace by viewModel.workPlace.collectAsStateWithLifecycle()
    val updateEvent by viewModel.updateEvent.collectAsStateWithLifecycle(null)
    val deleteEvent by viewModel.deleteEvent.collectAsStateWithLifecycle(null)

    LaunchedEffect(workPlaceEditId) {
        viewModel.loadWorkPlaceById(workPlaceEditId)
    }

    LaunchedEffect(updateEvent) {
        updateEvent?.let { popBackStack() }
    }

    LaunchedEffect(deleteEvent) {
        deleteEvent?.let { popAllBackStack(MainMenuRoute.Home) }
    }

    workPlace?.let { workPlace ->
        var workPlaceName by remember { mutableStateOf(workPlace.name) }
        var wage by remember { mutableStateOf(workPlace.wage.toString()) }
        var workDays by remember { mutableStateOf(workPlace.workDays) }
        var selectedPayDay by remember { mutableStateOf(workPlace.payDay.toUiModel()) }
        var workTime by remember { mutableStateOf(workPlace.workTime.toUiModel()) }
        var breakTime by remember { mutableStateOf(workPlace.breakTime.toString()) }
        var selectedWorkPlaceCardColor by remember { mutableStateOf(Color(workPlace.workPlaceCardColor)) }
        var isWeeklyHolidayAllowance by remember { mutableStateOf(workPlace.isWeeklyHolidayAllowance) }
        var selectedTax by remember { mutableStateOf(workPlace.tax.toUiModel()) }

        val selectedWorkDayType by remember(workDays) {
            mutableStateOf(workDayTypeSetter(workDays))
        }

        val workDaysSummary by remember(workDays, selectedWorkDayType) {
            mutableStateOf(getWorkDaysSummary(workDays, selectedWorkDayType))
        }

        WorkPlaceEditScreen(
            padding = padding,
            popBackStack = popBackStack,
            workPlaceName = workPlaceName,
            onWorkPlaceNameChange = { workPlaceName = it },
            wage = wage,
            onWageChange = { wage = it },
            workDays = workDays,
            workDaysSummary = workDaysSummary,
            onWorkDaysChange = { workDays = it },
            selectedPayDay = selectedPayDay,
            onPayDayChange = { selectedPayDay = it },
            workTime = workTime,
            onWorkTimeChange = { workTime = it },
            breakTime = breakTime,
            onBreakTimeChange = { breakTime = it },
            selectedWorkPlaceCardColor = selectedWorkPlaceCardColor,
            onWorkPlaceCardColorChange = { selectedWorkPlaceCardColor = it },
            isWeeklyHolidayAllowance = isWeeklyHolidayAllowance,
            onWeeklyHolidayAllowanceChange = { isWeeklyHolidayAllowance = it },
            selectedTax = selectedTax,
            onTaxChange = { selectedTax = it },
            onWorkPlaceUpdated = {
                viewModel.updateWorkPlace(
                    WorkPlace(
                        id = workPlace.id,
                        name = workPlaceName,
                        wage = wage.toLongOrNull() ?: 0L,
                        workDays = workDays,
                        payDay = selectedPayDay.toDomain(),
                        workTime = workTime.toDomain(),
                        breakTime = breakTime.toIntOrNull() ?: 0,
                        workPlaceCardColor = selectedWorkPlaceCardColor.toArgb(),
                        isWeeklyHolidayAllowance = isWeeklyHolidayAllowance,
                        tax = selectedTax.rate
                    )
                )
            },
            onDeleteWorkPlace = { viewModel.deleteWorkPlace(workPlace) }
        )
    }
}

@Composable
fun WorkPlaceEditScreen(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    workPlaceName: String,
    onWorkPlaceNameChange: (String) -> Unit,
    wage: String,
    onWageChange: (String) -> Unit,
    workDays: List<LocalDate>,
    workDaysSummary: String,
    onWorkDaysChange: (List<LocalDate>) -> Unit,
    selectedPayDay: UiPayDay,
    onPayDayChange: (UiPayDay) -> Unit,
    workTime: UiWorkTime,
    onWorkTimeChange: (UiWorkTime) -> Unit,
    breakTime: String,
    onBreakTimeChange: (String) -> Unit,
    selectedWorkPlaceCardColor: Color,
    onWorkPlaceCardColorChange: (Color) -> Unit,
    isWeeklyHolidayAllowance: Boolean,
    onWeeklyHolidayAllowanceChange: (Boolean) -> Unit,
    selectedTax: UiTaxType,
    onTaxChange: (UiTaxType) -> Unit,
    onWorkPlaceUpdated: () -> Unit,
    onDeleteWorkPlace: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showWorkDayDialog by remember { mutableStateOf(false) }
    var showPayDayDialog by remember { mutableStateOf(false) }
    var showBreakTimeDialog by remember { mutableStateOf(false) }
    var showWorkPlaceCardColorDialog by remember { mutableStateOf(false) }
    var showTaxDialog by remember { mutableStateOf(false) }

    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    val isWorkPlaceNameValid = workPlaceName.isNotBlank()
    val isWageValid = wage.isNotBlank()
    val isWorkDaysValid = workDays.isNotEmpty()
    val isSaveEnabled = isWorkPlaceNameValid && isWageValid && isWorkDaysValid

    Column(
        Modifier
            .padding(padding)
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding()
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
                text = "근무지 수정",
                modifier = Modifier.align(Alignment.Center)
            )
            OutlinedButton(
                onClick = { showDeleteDialog = true },
                modifier = Modifier.align(Alignment.CenterEnd),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, LightBlue)
            ) {
                Text(text = "근무지 삭제", color = Red)
            }
        }

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SubtitleText(text = "근무지 명")
                if (!isWorkPlaceNameValid) {
                    Text("*", color = Color.Red, modifier = Modifier.padding(start = 4.dp))
                }
            }

            InputTextField(
                text = workPlaceName,
                onValueChange = onWorkPlaceNameChange,
                placeholder = "근무지명을 입력하세요. (15자 이하)"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                SubtitleText(text = "시급")
                if (!isWageValid) {
                    Text("*", color = Color.Red, modifier = Modifier.padding(start = 4.dp))
                }
            }

            InputNumberField(
                text = wage,
                onValueChange = onWageChange,
                placeholder = "시급을 입력하세요.",
            )

            Spacer(modifier = Modifier.height(16.dp))

            SubtitleText(text = "일하는 시간 설정")

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
                SubtitleText(text = "휴게 시간 설정")
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SubtitleText(text = "일하는 날짜 설정")
                    if (!isWorkDaysValid) {
                        Text("*", color = Color.Red, modifier = Modifier.padding(start = 4.dp))
                    }
                }
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

            SubtitleText(text = "월급일 설정")
            PayDaySelector(
                selectedPayDay = selectedPayDay,
                onPayDayChange = { payDay ->
                    onPayDayChange(payDay)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SubtitleText(text = "주휴수당 설정")
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
                SubtitleText(text = "세금 설정")
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
                SubtitleText(text = "근무지 카드 색상 설정")
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(selectedWorkPlaceCardColor, shape = CircleShape)
                )
            }

            if (showDeleteDialog) {
                DeleteWorkPlaceDialog(
                    onConfirm = {
                        showDeleteDialog = false
                        onDeleteWorkPlace()
                    },
                    onDismiss = { showDeleteDialog = false }
                )
            }

            if (showWorkPlaceCardColorDialog) {
                WorkPlaceCardColorSelectionDialog(
                    selectedColor = selectedWorkPlaceCardColor,
                    onDismiss = { showWorkPlaceCardColorDialog = false },
                    onColorSelected = {
                        onWorkPlaceCardColorChange(it)
                        showWorkPlaceCardColorDialog = false
                    }
                )
            }

            if (showWorkDayDialog) {
                WorkDaySelectionDialog(
                    initialSelectedDates = workDays,
                    onDismiss = { showWorkDayDialog = false },
                    onSelect = { workDayType ->
                        onWorkDaysChange(
                            workDayType.dayOfWeeks.map { LocalDate.now().with(java.time.temporal.TemporalAdjusters.nextOrSame(it)) }
                        )
                        showWorkDayDialog = false
                    },
                    onCustomWorkDaysSelected = { dates ->
                        onWorkDaysChange(dates)
                        showWorkDayDialog = false
                    }
                )
            }

            if (showPayDayDialog) {
                PayDaySelectionDialog(
                    payDay = selectedPayDay,
                    onDismiss = { showPayDayDialog = false },
                    onConfirm = { updatedPayDay ->
                        onPayDayChange(updatedPayDay)
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
                        onTaxChange(it)
                        showTaxDialog = false
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onWorkPlaceUpdated,
                modifier = Modifier.fillMaxWidth(),
                enabled = isSaveEnabled,
                colors = ButtonDefaults.buttonColors(containerColor = LightBlue)
            ) {
                Text("수정 완료")
            }
        }
    }
}