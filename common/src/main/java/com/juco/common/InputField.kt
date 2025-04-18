package com.juco.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputTextField(
    text: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column {
        BasicTextField(
            value = text,
            onValueChange = { newText ->
                if (newText.length <= 15) {
                    onValueChange(newText)
                }
            },
            textStyle = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.primary),
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
    var rawText by remember { mutableStateOf(text) }
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text)) }

    Column {
        BasicTextField(
            value = textFieldValueState,
            onValueChange = { inputValue ->
                val filteredInput = inputValue.text.filter { it.isDigit() }
                val numericValue = filteredInput.toLongOrNull() ?: 0L

                if (numericValue <= 100_000_000) {
                    val prevLength = rawText.length
                    rawText = filteredInput

                    val formattedText = formatWithComma(rawText)
                    val cursorOffset =
                        (formattedText.length - formatWithComma(prevLength.toString()).length)
                    val newCursorPosition = (inputValue.selection.start + cursorOffset).coerceIn(
                        0,
                        formattedText.length
                    )

                    textFieldValueState =
                        TextFieldValue(formattedText, TextRange(newCursorPosition))
                    onValueChange(filteredInput)
                }
            },
            textStyle = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.primary),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            decorationBox = { innerTextField ->
                Box(Modifier.fillMaxWidth()) {
                    if (rawText.isEmpty()) Text(placeholder, color = Color.Gray)
                    innerTextField()
                }
            }
        )
        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray))
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
            textStyle = TextStyle(
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            ),
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