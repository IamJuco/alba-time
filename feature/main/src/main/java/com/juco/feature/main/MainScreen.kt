package com.juco.feature.main

import android.content.Context
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.juco.feature.main.component.AdmobBanner
import com.juco.feature.main.component.BottomNavigationBar
import com.juco.feature.main.component.MainNavHost
import com.juco.feature.main.navigation.MainNavigator
import com.juco.feature.main.navigation.rememberMainNavigator
import com.juco.feature.main.util.getAppVersionName
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
    context: Context = LocalContext.current
) {
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val snackBarHostState = remember { SnackbarHostState() }

    val onShowSnackBar: (String) -> Unit = { msg ->
        lifecycleScope.launch { snackBarHostState.showSnackbar(msg) }
    }

    val versionName = remember { getAppVersionName(context) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        content = { paddingValues ->
            MainNavHost(
                navigator = navigator,
                padding = paddingValues,
                onShowSnackBar = onShowSnackBar,
                admobBanner = { AdmobBanner() },
                versionName = versionName
            )
        },
        bottomBar = {
            BottomNavigationBar(
                modifier = Modifier.navigationBarsPadding(),
                currentMenu = navigator.currentMenu,
                onMenuSelected = { navigator.navigate(it) }
            )
        }
    )
}