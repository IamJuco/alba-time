package com.juco.feature.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.juco.feature.main.navigation.MainMenu

@Composable
fun BottomNavigationBar(
    currentMenu: MainMenu?,
    onMenuSelected: (MainMenu) -> Unit,
) {
    Column(
        modifier = Modifier.padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(Color.Gray)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            MainMenu.entries.forEach { menu ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painterResource(menu.iconResId),
                            contentDescription = menu.contentDescription,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text(menu.contentDescription) },
                    selected = menu == currentMenu,
                    onClick = {
                        onMenuSelected(menu)
                    }
                )
            }
        }
    }
}