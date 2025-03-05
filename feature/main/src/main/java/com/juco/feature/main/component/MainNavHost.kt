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
    onShowSnackBar: (String) -> Unit,
    admobBanner: @Composable () -> Unit,
    versionName: String
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination
    ) {
        homeNavGraph(
            padding = padding,
            navigateToWorkPlaceAdder = navigator::navigateToWorkPlaceAdder,
            navigateToWorkPlaceDetail = navigator::navigateToWorkPlaceDetail,
            versionName = versionName
        )
        calendarNavGraph(
            padding = padding,
            admobBanner = admobBanner
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