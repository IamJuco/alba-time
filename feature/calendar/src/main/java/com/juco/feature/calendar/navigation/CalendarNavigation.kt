package com.juco.feature.calendar.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.juco.domain.navigation.MainMenuRoute
import com.juco.feature.calendar.CalendarRoute

fun NavController.navigateCalendar(navOptions: NavOptions) {
    navigate(MainMenuRoute.Calendar, navOptions)
}

fun NavGraphBuilder.calendarNavGraph(
    padding: PaddingValues,
    admobBanner: @Composable () -> Unit
) {
    composable<MainMenuRoute.Calendar> {
        CalendarRoute(
            padding = padding,
            admobBanner = admobBanner
        )
    }
}