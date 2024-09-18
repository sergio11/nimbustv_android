package com.dreamsoftware.nimbustv.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dreamsoftware.nimbustv.ui.screens.home.HomeScreen
import com.dreamsoftware.nimbustv.ui.screens.settings.SettingsScreen

@Composable
fun DashboardNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {
            with(navController) {
                HomeScreen()
            }
        }

        composable(Screen.Settings.route) {
            with(navController) {
                SettingsScreen(
                    onGoToSubscriptions = {
                        navigate(Screen.Subscription.route)
                    },
                    onBackPressed = {
                        popBackStack()
                    }
                )
            }
        }
    }
}