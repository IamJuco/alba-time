package com.juco.workplacedetail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.juco.domain.navigation.RouteModel
import com.juco.workplacedetail.WorkPlaceDetailRoute

fun NavController.navigateToWorkPlaceDetail(workPlaceId: Int, navOptions: NavOptions? = null) {
    navigate(RouteModel.WorkPlaceDetail(workPlaceId), navOptions)
}

fun NavGraphBuilder.workPlaceDetailNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit,
) {
    composable<RouteModel.WorkPlaceDetail> { navBackStackEntry ->
        val workPlaceWorkId = navBackStackEntry.toRoute<RouteModel.WorkPlaceDetail>().workPlaceId
        WorkPlaceDetailRoute(
            padding = padding,
            popBackStack = popBackStack,
            workPlaceDetailId = workPlaceWorkId
        )
    }
}