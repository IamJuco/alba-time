package com.juco.feature.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.juco.common.formatWithComma
import com.juco.domain.model.WorkPlace

// 근무 시급 카드
@OptIn(ExperimentalLayoutApi::class) // FlowRow
@Composable
fun WorkWageCard(workPlaces: List<WorkPlace>) {
    val maxWorkPlaceCount = 2
    val displayWorkPlaces = workPlaces.take(maxWorkPlaceCount)
    val remainingCount = workPlaces.size - maxWorkPlaceCount

    FlowRow(
        modifier = Modifier.padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        displayWorkPlaces.forEach { workPlace ->
            WorkWageChip(formatWithComma(workPlace.wage))
        }

        if (remainingCount > 0) {
            WorkWageChip("외 ${remainingCount}개", isRemaining = true)
        }
    }
}

@Composable
fun WorkWageChip(name: String, isRemaining: Boolean = false) {
    Box(
        modifier = Modifier
            .background(
                if (isRemaining) Color.LightGray else Color(0xFFE0F7FA),
                RoundedCornerShape(4.dp)
            )
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = name,
            fontSize = 10.sp,
            fontWeight = if (isRemaining) FontWeight.Bold else FontWeight.SemiBold,
            color = if (isRemaining) Color.Gray else Color.DarkGray
        )
    }
}