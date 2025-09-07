package com.juco.designsystem.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juco.designsystem.textfield.TitleText
import com.juco.designsystem.theme.AlbaTimeTheme

@Composable
fun PreviousTopBar(
    title: String,
    onPopBackStack: () -> Unit,
    centerEndAction: (@Composable RowScope.() -> Unit)? = null
) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
    ) {
        IconButton(
            onClick = { onPopBackStack() },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "뒤로가기",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        TitleText(
            text = title,
            modifier = Modifier.align(Alignment.Center)
        )

        if (centerEndAction != null) {
            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) { centerEndAction() }
        }

    }
}

@Preview
@Composable
private fun PreviousTopBarPreview() {
    AlbaTimeTheme {
        PreviousTopBar(
            title = "알바타임",
            onPopBackStack = {}
        )
    }
}