package com.juco.workplacesetting.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.juco.domain.navigation.RouteModel
import com.juco.workplacesetting.WorkPlaceAdderRoute

fun NavController.navigateWorkPlaceAdder(navOptions: NavOptions) {
    navigate(RouteModel.WorkPlaceAdder, navOptions)
}

fun NavGraphBuilder.workPlaceAdderNavGraph(
    padding: PaddingValues
) {
    composable<RouteModel.WorkPlaceAdder> {
        WorkPlaceAdderRoute(
            padding = padding
        )
    }
}