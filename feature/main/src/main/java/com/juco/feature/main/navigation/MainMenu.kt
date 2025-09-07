package com.juco.feature.main.navigation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import com.juco.common.navigation.MainMenuRoute
import com.juco.common.navigation.RouteModel
import com.juco.designsystem.R

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
    ),
    COMMUNITY(
        iconResId = R.drawable.ic_community_24dp,
        contentDescription = "Community",
        route = MainMenuRoute.Community
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (RouteModel) -> Boolean): MainMenu? {
            return entries.find { predicate(it.route) }
        }
    }
}