package com.juco.workplaceedit.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.juco.domain.navigation.RouteModel
import com.juco.workplaceedit.WorkPlaceEditRoute

fun NavController.navigateToWorkPlaceEdit(workPlaceId: Int, navOptions: NavOptions? = null) {
    navigate(RouteModel.WorkPlaceEdit(workPlaceId), navOptions)
}

fun NavGraphBuilder.workPlaceEditNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    popAllBackStack: (RouteModel) -> Unit
) {
    composable<RouteModel.WorkPlaceEdit> { navBackStackEntry ->
        val workPlaceWorkId = navBackStackEntry.toRoute<RouteModel.WorkPlaceEdit>().workPlaceId
        WorkPlaceEditRoute(
            padding = padding,
            popBackStack = popBackStack,
            popAllBackStack = popAllBackStack,
            workPlaceEditId = workPlaceWorkId
        )
    }
}