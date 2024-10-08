package com.dreamsoftware.nimbustv.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dreamsoftware.nimbustv.ui.player.video.VideoPlayerScreen
import com.dreamsoftware.nimbustv.ui.screens.favorites.FavoritesScreen
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
                HomeScreen(
                    onPlayChannel = { channelId ->
                        navigate(Screen.VideoPlayer.buildRoute(channelId))
                    }
                )
            }
        }

        composable(Screen.Favorite.route) {
            with(navController) {
                FavoritesScreen(
                    onBackPressed = {
                        popBackStack()
                    }
                )
            }
        }

        composable(Screen.VideoPlayer.route) { navBackStackEntry ->
            navBackStackEntry.arguments?.let(Screen.VideoPlayer::parseArgs)?.let { args ->
                with(navController) {
                    VideoPlayerScreen(args = args) {
                        popBackStack()
                    }
                }
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