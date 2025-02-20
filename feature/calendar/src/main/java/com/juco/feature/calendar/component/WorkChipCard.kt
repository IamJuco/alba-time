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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juco.domain.model.WorkPlace
import com.juco.feature.calendar.util.workTimeCalculator

data class WorkChipModel(
    val text: String,
    val color: Int,
    val isPayDay: Boolean = false
)

@OptIn(ExperimentalLayoutApi::class) // FlowRow
@Composable
fun WorkChipCard(
    workPlaces: List<WorkPlace>,
    payDayWorkPlaces: List<WorkPlace>
) {
    val maxVisibleCount = 2 // Chip Calendar Cell에 보일 갯수

    val allChips = buildList {
        workPlaces.forEach { workPlace ->
            add(WorkChipModel(workPlace.workTimeCalculator(), workPlace.workPlaceCardColor))
        }
        payDayWorkPlaces.forEach { workPlace ->
            add(WorkChipModel("월급날", workPlace.workPlaceCardColor, isPayDay = true))
        }
    }

    val displayChips = allChips.take(maxVisibleCount)
    val remainingCount = allChips.size - maxVisibleCount

    FlowRow(
        modifier = Modifier.padding(start = 2.dp, end = 2.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        displayChips.forEach { chip ->
            if (chip.isPayDay) {
                PayDayChip(chip)
            } else {
                WorkTimeChip(chip)
            }
        }

        if (remainingCount > 0) {
            WorkTimeChip(WorkChipModel("외 ${remainingCount}개", Color.LightGray.toArgb(), isPayDay = false), isRemaining = true)
        }
    }
}

@Composable
fun WorkTimeChip(chip: WorkChipModel, isRemaining: Boolean = false) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isRemaining) Color.LightGray else Color(chip.color),
                RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = chip.text,
            fontSize = 8.sp,
            fontWeight = if (isRemaining) FontWeight.Bold else FontWeight.SemiBold,
            color = if (isRemaining) Color.Gray else Color.DarkGray
        )
    }
}

@Composable
fun PayDayChip(chip: WorkChipModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(chip.color), RoundedCornerShape(4.dp))
            .padding(horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = chip.text,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
    }
}