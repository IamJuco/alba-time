package com.juco.feature.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juco.feature.main.navigation.MainMenu

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    currentMenu: MainMenu?,
    onMenuSelected: (MainMenu) -> Unit,
) {
    Column(
        modifier = modifier
//        modifier = Modifier.padding(WindowInsets.navigationBars.asPaddingValues())
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(Color.Gray)
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .height(56.dp)
        ) {
            MainMenu.entries.forEach { menu ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painterResource(menu.iconResId),
                            contentDescription = menu.contentDescription,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = {
                        Text(
                            text = menu.contentDescription,
                            fontSize = 10.sp
                        )
                    },
                    selected = menu == currentMenu,
                    onClick = {
                        onMenuSelected(menu)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}