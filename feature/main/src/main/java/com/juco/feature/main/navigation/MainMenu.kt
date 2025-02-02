package com.juco.feature.main.navigation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import com.juco.domain.navigation.MainMenuRoute
import com.juco.domain.navigation.RouteModel
import com.juco.feature.main.R

enum class MainMenu(
    @DrawableRes
    val iconResId: Int,
    val contentDescription: String,
    val route: RouteModel
) {
    HOME(
        iconResId = R.drawable.ic_home_24dp,
        contentDescription = "Home",
        route = MainMenuRoute.Home,
    ),
    CALENDAR(
        iconResId = R.drawable.ic_calendar_24dp,
        contentDescription = "Calendar",
        route = MainMenuRoute.Calendar,
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (RouteModel) -> Boolean): MainMenu? {
            return entries.find { predicate(it.route) }
        }
    }
}