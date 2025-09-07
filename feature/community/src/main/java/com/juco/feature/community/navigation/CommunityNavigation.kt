package com.juco.feature.community.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.juco.common.navigation.MainMenuRoute
import com.juco.feature.community.CommunityRoute

fun NavController.navigateCommunity(navOptions: NavOptions) {
    navigate(MainMenuRoute.Community, navOptions)
}

fun NavGraphBuilder.communityNavGraph(
    padding: PaddingValues
) {
    composable<MainMenuRoute.Community> {
        CommunityRoute(
            padding = padding
        )
    }
}