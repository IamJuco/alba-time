package com.juco.feature.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juco.domain.model.WorkPlace

// 근무지명 카드
@Composable
fun WorkPlaceCard(workPlaces: List<WorkPlace>) {
    val displayText = when {
        workPlaces.size == 1 -> workPlaces.first().name
        else -> "${workPlaces.first().name} 외 ${workPlaces.size - 1}개"
    }

    Box(
        modifier = Modifier
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = displayText,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray
        )
    }
}