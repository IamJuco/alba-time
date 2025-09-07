package com.juco.feature.post.create.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.juco.common.navigation.RouteModel
import com.juco.feature.post.create.PostCreateRoute

fun NavController.navigatePostCreate(navOptions: NavOptions) {
    navigate(RouteModel.PostCreate, navOptions)
}

fun NavGraphBuilder.postCreateNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit
) {
    composable<RouteModel.PostCreate> {
        PostCreateRoute(
            padding = padding,
            popBackStack = popBackStack
        )
    }
}