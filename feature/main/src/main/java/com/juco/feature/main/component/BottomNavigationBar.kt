package com.juco.feature.main.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.juco.feature.main.navigation.MainMenu

@Composable
fun BottomNavigationBar(
    currentMenu: MainMenu?,
    onMenuSelected: (MainMenu) -> Unit,
) {
    NavigationBar {
        MainMenu.entries.forEach { menu ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(menu.iconResId),
                        contentDescription = menu.contentDescription
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