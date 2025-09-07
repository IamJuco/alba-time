package com.juco.feature.post.read.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.juco.common.navigation.RouteModel
import com.juco.feature.post.read.PostReadRoute

fun NavController.navigatePostRead(postId: Int, navOptions: NavOptions) {
    navigate(RouteModel.PostRead(postId), navOptions)
}

fun NavGraphBuilder.postReadNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit
) {
    composable<RouteModel.PostRead> {
        PostReadRoute(
            padding = padding,
            popBackStack = popBackStack
        )
    }
}