package com.juco.feature.calendar.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.juco.domain.navigation.RouteModel
import com.juco.feature.calendar.CalendarRoute

fun NavController.navigateCalendar(navOptions: NavOptions) {
    navigate(RouteModel.Calendar, navOptions)
}

fun NavGraphBuilder.calendarNavGraph(
    padding: PaddingValues,
    onShowSnackBar: (String) -> Unit
) {
    composable<RouteModel.Calendar> {
        CalendarRoute(
            padding = padding,
            onShowSnackBar = onShowSnackBar
        )
    }
}