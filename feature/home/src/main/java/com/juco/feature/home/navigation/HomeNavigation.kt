package com.juco.feature.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.juco.domain.navigation.MainMenuRoute
import com.juco.feature.home.HomeRoute

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(MainMenuRoute.Home, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    navigateToWorkPlaceAdder: () -> Unit,
    navigateToWorkPlaceDetail: (Int) -> Unit
) {
    composable<MainMenuRoute.Home> {
        HomeRoute(
            padding = padding,
            navigateToWorkPlaceAdder = navigateToWorkPlaceAdder,
            navigateToWorkPlaceDetail = navigateToWorkPlaceDetail
        )
    }
}