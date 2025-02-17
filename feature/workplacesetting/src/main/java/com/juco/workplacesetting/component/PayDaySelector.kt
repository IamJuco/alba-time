package com.juco.workplacesetting.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
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
import com.juco.feature.workplacesetting.R
import com.juco.workplacesetting.model.PayDayType
import com.juco.workplacesetting.model.PayDayValue

@Composable
fun PayDaySelector(
    selectedPayDayType: PayDayType,
    selectedPayDayValue: PayDayValue,
    onPayDayTypeChange: (PayDayType) -> Unit,
    onPayDayValueChange: (PayDayValue) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    val payDayText = when (selectedPayDayValue) {
        is PayDayValue.Monthly -> selectedPayDayValue.displayName
        is PayDayValue.Weekly -> selectedPayDayValue.displayName
        PayDayValue.Custom -> "직접 설정"
    }

    val payDayOptions = PayDayType.entries.toTypedArray()

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            payDayOptions.forEach { option ->
                Button(
                    onClick = {
                        onPayDayTypeChange(option)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedPayDayType == option) Color(0xFF90CAF9) else Color.LightGray,
                        contentColor = if (selectedPayDayType == option) Color.White else Color.Black
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(option.displayName, fontSize = 14.sp)
                }
            }
        }

        if (selectedPayDayType in listOf(PayDayType.MONTHLY, PayDayType.WEEKLY)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .clickable { showDialog = true }
                        .padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = payDayText,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dropdown_24dp),
                        contentDescription = "급여일 설정",
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "${selectedPayDayType.displayName}을 받습니다.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        }

        if (showDialog) {
            PayDaySelectionDialog(
                payDayType = selectedPayDayType,
                onDismiss = { showDialog = false },
                onConfirm = { value ->
                    onPayDayValueChange(value)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun PayDaySelectionDialog(
    payDayType: PayDayType,
    onDismiss: () -> Unit,
    onConfirm: (PayDayValue) -> Unit
) {
    val monthlyOptions = listOf("1일", "5일", "10일", "15일", "20일", "25일", "말일")
    val weeklyOptions = listOf("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일")

    val options = when (payDayType) {
        PayDayType.MONTHLY -> monthlyOptions
        PayDayType.WEEKLY -> weeklyOptions
        else -> emptyList()
    }

    var selectedOption by remember { mutableStateOf(options.first()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "${payDayType.displayName}일 선택",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                options.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = selectedOption == option,
                                onClick = { selectedOption = option }
                            )
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedOption == option,
                            onClick = { selectedOption = option }
                        )
                        Text(
                            text = option,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val selectedValue = PayDayValue.fromDisplayName(selectedOption, payDayType)
                    onConfirm(selectedValue)
                }
            ) {
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
