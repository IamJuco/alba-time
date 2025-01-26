package com.juco.feature.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.juco.domain.navigation.RouteModel
import com.juco.feature.home.HomeRoute

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(RouteModel.Home, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    onShowSnackBar: (String) -> Unit
) {
    composable<RouteModel.Home> {
        HomeRoute(
            padding = padding,
            onShowSnackBar = onShowSnackBar
        )
    }
}