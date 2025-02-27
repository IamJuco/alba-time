package com.juco.feature.main.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.juco.feature.calendar.navigation.calendarNavGraph
import com.juco.feature.home.navigation.homeNavGraph
import com.juco.feature.main.navigation.MainNavigator
import com.juco.workplacedetail.navigation.workPlaceDetailNavGraph
import com.juco.workplacesetting.navigation.workPlaceAdderNavGraph

@Composable
fun MainNavHost(
    navigator: MainNavigator,
    padding: PaddingValues,
    onShowSnackBar: (String) -> Unit
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination
    ) {
        homeNavGraph(
            padding = padding,
            navigateToWorkPlaceAdder = navigator::navigateToWorkPlaceAdder,
            navigateToWorkPlaceDetail = navigator::navigateToWorkPlaceDetail
        )
        calendarNavGraph(
            padding = padding
        )
        workPlaceAdderNavGraph(
            padding = padding,
            popBackStack = navigator::popBackStack,
        )
        workPlaceDetailNavGraph(
            padding = padding,
            popBackStack = navigator::popBackStack,
        )
    }
}