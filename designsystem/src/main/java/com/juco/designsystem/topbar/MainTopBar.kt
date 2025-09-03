package com.juco.designsystem.topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juco.designsystem.theme.AlbaTimeTheme

@Composable
fun MainTopBar(
    title: String,
    subTitle: String? = null
) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
        if (subTitle != null) {
            Text(
                text = subTitle,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterEnd).padding(end = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainTopBarPreview() {
    AlbaTimeTheme {
        MainTopBar(
            title = "알바타임",
            subTitle = "앱 버전: 1.0.0"
        )
    }
}