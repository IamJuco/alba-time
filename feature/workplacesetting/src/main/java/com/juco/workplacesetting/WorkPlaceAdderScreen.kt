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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.juco.feature.workplacesetting.R

@Composable
fun WorkPlaceAdderRoute(
    padding: PaddingValues = PaddingValues(),
    viewModel: WorkPlaceViewModel = hiltViewModel()
) {
    val workPlaceName by viewModel.workPlaceName.collectAsStateWithLifecycle()
    val wage by viewModel.wage.collectAsStateWithLifecycle()

    var selectedWorkDays by remember { mutableStateOf("설정 안됨") }

    WorkPlaceAdderScreen(
        padding = padding,
        workPlaceName = workPlaceName,
        onWorkPlaceNameChange = { viewModel.workPlaceName.value = it },
        wage = wage,
        onWageChange = { viewModel.wage.value = it },
        selectedWorkDays = selectedWorkDays,
        onWorkDaysSelected = { selectedWorkDays = it },
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
    selectedWorkDays: String,
    onWorkDaysSelected: (String) -> Unit,
    onSaveClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
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
            TextField(
                text = workPlaceName,
                onValueChange = onWorkPlaceNameChange,
                placeholder = "근무지명을 입력하세요"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("시급", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            TextField(
                text = wage,
                onValueChange = onWageChange,
                placeholder = "시급을 입력하세요",
                keyboardType = KeyboardType.Number
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDialog = true }
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "일하는 날짜 설정",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = selectedWorkDays, color = Color.Gray, fontSize = 18.sp)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dropdown_24dp),
                        contentDescription = "일하는 날짜 설정",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            if (showDialog) {
                WorkDaySelectionDialog(
                    onDismiss = { showDialog = false },
                    onSelect = { selectedOption ->
                        onWorkDaysSelected(selectedOption)
                        showDialog = false
                    }
                )
            }
        }
    }
}


@Composable
fun TextField(
    text: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            textStyle = TextStyle(fontSize = 16.sp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
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
fun WorkDaySelectionDialog(
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "일하는 날짜 선택") },
        text = {
            Column {
                WorkDayOption("월~금 (주 5일 근무)", onSelect, onDismiss)
                WorkDayOption("주말 (토, 일 근무)", onSelect, onDismiss)
                WorkDayOption("직접 설정", onSelect, onDismiss)
            }
        },
        confirmButton = {}
    )
}

@Composable
fun WorkDayOption(
    text: String,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelect(text)
                onDismiss()
            }
            .padding(16.dp)
    ) {
        Text(text = text, fontSize = 18.sp)
    }
}

//
//@Composable
//@Preview(showBackground = true)
//fun PreviewWorkPlaceAdderScreen() {
//    WorkPlaceAdderScreen(padding = PaddingValues())
//}