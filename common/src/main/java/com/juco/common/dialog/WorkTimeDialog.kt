package com.juco.common.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SamsungStyleTimePickerDialog(
    initialTime: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var isAm by remember { mutableStateOf(initialTime.split(":")[0].toInt() < 12) }
    var hour by remember { mutableIntStateOf(initialTime.split(":")[0].toInt().let { if (it == 0) 12 else it % 12 }) }
    var minute by remember { mutableIntStateOf(initialTime.split(":")[1].toInt()) }

    val hours = (1..12).map { String.format("%02d", it) }
    val minutes = (0..59).map { String.format("%02d", it) }
    val amPm = listOf("오전", "오후")

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val finalHour = if (isAm) {
                    if (hour == 12) 0 else hour
                } else {
                    if (hour == 12) 12 else hour + 12
                }
                val formattedTime = String.format("%02d:%02d", finalHour, minute)
                onConfirm(formattedTime)
            }) {
                Text("확인")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        },
        title = { Text("시간 선택") },
        text = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .height(120.dp)
                            .width(80.dp)
                            .background(Color.LightGray.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        LazyColumn(
                            modifier = Modifier.height(120.dp)
                        ) {
                            items(amPm) { period ->
                                Text(
                                    text = period,
                                    fontSize = 24.sp,
                                    fontWeight = if ((period == "오전" && isAm) || (period == "오후" && !isAm)) FontWeight.Bold else FontWeight.Normal,
                                    color = if ((period == "오전" && isAm) || (period == "오후" && !isAm)) Color.Black else Color.Gray,
                                    modifier = Modifier
                                        .padding(vertical = 4.dp)
                                        .clickable {
                                            isAm = (period == "오전")
                                        }
                                )
                            }
                        }
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("시", fontWeight = FontWeight.Bold)
                    Box(
                        modifier = Modifier
                            .height(120.dp)
                            .width(80.dp)
                            .background(Color.LightGray.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        LazyColumn(
                            modifier = Modifier.height(120.dp)
                        ) {
                            items(hours) { h ->
                                Text(
                                    text = h,
                                    fontSize = 24.sp,
                                    fontWeight = if (h.toInt() == hour) FontWeight.Bold else FontWeight.Normal,
                                    color = if (h.toInt() == hour) Color.Black else Color.Gray,
                                    modifier = Modifier
                                        .padding(vertical = 4.dp)
                                        .clickable { hour = h.toInt() }
                                )
                            }
                        }
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("분", fontWeight = FontWeight.Bold)
                    Box(
                        modifier = Modifier
                            .height(120.dp)
                            .width(80.dp)
                            .background(Color.LightGray.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        LazyColumn(
                            modifier = Modifier.height(120.dp)
                        ) {
                            items(minutes) { m ->
                                Text(
                                    text = m,
                                    fontSize = 24.sp,
                                    fontWeight = if (m.toInt() == minute) FontWeight.Bold else FontWeight.Normal,
                                    color = if (m.toInt() == minute) Color.Black else Color.Gray,
                                    modifier = Modifier
                                        .padding(vertical = 4.dp)
                                        .clickable { minute = m.toInt() }
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}