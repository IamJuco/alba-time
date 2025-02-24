package com.juco.workplacesetting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juco.common.formatWithComma

@Composable
fun InputTextField(
    text: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            textStyle = TextStyle(fontSize = 16.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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
fun InputNumberField(
    text: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column {
        BasicTextField(
            value = formatWithComma(text),
            onValueChange = { input ->
                val filteredInput = input.filter { it.isDigit() }
                onValueChange(filteredInput)
            },
            textStyle = TextStyle(fontSize = 16.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
fun BreakTimeInputTextField(
    breakTime: String,
    onBreakTimeChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        BasicTextField(
            value = breakTime,
            onValueChange = { input ->
                val filteredInput = input.filter { it.isDigit() }
                onBreakTimeChange(filteredInput)
            },
            textStyle = TextStyle(fontSize = 24.sp, textAlign = TextAlign.Center),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    if (breakTime.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Color.Gray,
                        )
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