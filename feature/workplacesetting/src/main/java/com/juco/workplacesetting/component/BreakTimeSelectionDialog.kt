package com.juco.workplacesetting.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun BreakTimeSelectionDialog(
    currentBreakTime: String,
    onDismiss: () -> Unit,
    onBreakTimeSelected: (String) -> Unit
) {
    val options = listOf("없음", "30분", "60분", "직접 입력")
    var isCustomInput by remember { mutableStateOf(false) }
    var customBreakTime by remember { mutableStateOf(if (currentBreakTime.isEmpty()) "" else currentBreakTime) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "휴게 시간 설정",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                options.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (option == "직접 입력") {
                                    isCustomInput = true
                                    customBreakTime = ""
                                } else {
                                    val selectedValue = if (option == "없음") "0" else option.replace("분", "")
                                    onBreakTimeSelected(selectedValue)
                                    onDismiss()
                                }
                            }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = option,
                            fontSize = 18.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                if (isCustomInput) {
                    Spacer(modifier = Modifier.height(8.dp))

                    BreakTimeInputTextField(
                        breakTime = customBreakTime,
                        onBreakTimeChange = { customBreakTime = it },
                        placeholder = "분 단위 입력"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            onBreakTimeSelected(customBreakTime.ifEmpty { "0" })
                            onDismiss()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("확인")
                    }
                }
            }
        }
    }
}