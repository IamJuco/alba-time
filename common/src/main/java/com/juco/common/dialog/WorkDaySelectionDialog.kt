package com.juco.common.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juco.common.model.WorkDayType
import java.time.LocalDate

@Composable
fun WorkDaySelectionDialog(
    initialSelectedDates: List<LocalDate>,
    onDismiss: () -> Unit,
    onSelect: (WorkDayType) -> Unit,
    onCustomWorkDaysSelected: (List<LocalDate>) -> Unit
) {
    var showCalendarDialog by remember { mutableStateOf(false) }

    AlertDialog(
        containerColor = MaterialTheme.colorScheme.background,
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
        Text(text = text, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
    }
}