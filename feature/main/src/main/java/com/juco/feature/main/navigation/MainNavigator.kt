package com.juco.feature.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.juco.feature.calendar.navigation.navigateCalendar
import com.juco.feature.home.navigation.navigateHome
import com.juco.workplacedetail.navigation.navigateToWorkPlaceDetail
import com.juco.workplaceedit.navigation.navigateToWorkPlaceEdit
import com.juco.workplacesetting.navigation.navigateWorkPlaceAdder

class MainNavigator(
    val navController: NavHostController
) {
    val startDestination = MainMenu.HOME.route
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination
    val currentMenu: MainMenu?
        @Composable get() = MainMenu.find { menu ->
            currentDestination?.hasRoute(menu::class) == true
        }
    private val singleTopOptions = navOptions {
        launchSingleTop = true
        restoreState = true
    }

    fun navigate(menu: MainMenu) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = menu == MainMenu.HOME
            }
            launchSingleTop = true
        }

        when (menu) {
            MainMenu.HOME -> navController.navigateHome(navOptions)
            MainMenu.CALENDAR -> navController.navigateCalendar(navOptions)
        }
    }

    fun navigateToWorkPlaceAdder() = navController.navigateWorkPlaceAdder(navOptions = singleTopOptions)
    fun navigateToWorkPlaceDetail(workPlaceId: Int) = navController.navigateToWorkPlaceDetail(workPlaceId = workPlaceId, navOptions = singleTopOptions)
    fun navigateToWorkPlaceEdit(workPlaceId: Int) = navController.navigateToWorkPlaceEdit(workPlaceId = workPlaceId, navOptions = singleTopOptions)

    fun popBackStack() {
        navController.popBackStack()
    }
}

@Composable
fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}