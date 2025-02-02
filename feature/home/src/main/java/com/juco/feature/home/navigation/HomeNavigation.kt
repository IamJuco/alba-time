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
    onShowSnackBar: (String) -> Unit,
    navigateToWorkPlaceAdder: () -> Unit
) {
    composable<MainMenuRoute.Home> {
        HomeRoute(
            padding = padding,
            onShowSnackBar = onShowSnackBar,
            navigateToWorkPlaceAdder = navigateToWorkPlaceAdder
        )
    }
}