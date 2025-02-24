package com.juco.feature.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juco.common.formatWithComma
import com.juco.domain.model.WorkPlace

// Home에서 보일 근무지 카드
@Composable
fun WorkPlaceCard(
    workPlace: WorkPlace,
//    onDeleteClick: (WorkPlace) -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(100.dp)
//            .clickable { onDeleteClick(workPlace) },
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(workPlace.workPlaceCardColor)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "근무지: ${workPlace.name}",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "시급: ${formatWithComma(workPlace.wage)}원",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}