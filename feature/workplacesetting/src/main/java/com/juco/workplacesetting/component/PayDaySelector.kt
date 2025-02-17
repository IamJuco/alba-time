package com.juco.workplacesetting.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PayDaySelector(
    selectedSalaryType: String,
    onSalaryTypeChange: (String) -> Unit,
    onCustomSelected: () -> Unit
) {
    val options = listOf("월급", "주급", "직접설정")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        options.forEach { option ->
            Button(
                onClick = {
                    if (option == "직접설정") {
                        onCustomSelected()
                        onSalaryTypeChange(option)
                    } else {
                        onSalaryTypeChange(option)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = when {
                        selectedSalaryType == option -> Color(0xFF90CAF9)
                        else -> Color.LightGray
                    },
                    contentColor = if (selectedSalaryType == option) Color.White else Color.Black
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(option, fontSize = 14.sp)
            }
        }
    }
}