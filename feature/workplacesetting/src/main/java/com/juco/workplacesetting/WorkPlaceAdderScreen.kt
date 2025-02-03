package com.juco.workplacesetting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.juco.domain.model.WorkPlace

@Composable
fun WorkPlaceAdderRoute(
    padding: PaddingValues = PaddingValues(),
    viewModel: WorkPlaceViewModel = hiltViewModel()
) {
    val workPlaceName by viewModel.workPlaceName.collectAsStateWithLifecycle()
    val wage by viewModel.wage.collectAsStateWithLifecycle()

    WorkPlaceAdderScreen(
        padding = padding,
        workPlaceName = workPlaceName,
        onWorkPlaceNameChange = { viewModel.workPlaceName.value = it },
        wage = wage,
        onWageChange = { viewModel.wage.value = it },
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
    onSaveClick: () -> Unit
) {
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
            Button(
                onClick = onSaveClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("저장")
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

//
//@Composable
//@Preview(showBackground = true)
//fun PreviewWorkPlaceAdderScreen() {
//    WorkPlaceAdderScreen(padding = PaddingValues())
//}