package com.juco.feature.calendar.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WorkPlaceCard(
    workPlaceName: String,
    workPlaceCardColor: Int
) {
    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = Color(workPlaceCardColor)
        )
    ) {
        Text(
            modifier = Modifier.padding(4.dp).align(Alignment.CenterHorizontally),
            text = workPlaceName,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}