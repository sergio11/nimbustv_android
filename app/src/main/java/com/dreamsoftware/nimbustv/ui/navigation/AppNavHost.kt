package com.dreamsoftware.nimbustv.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dreamsoftware.nimbustv.ui.screens.onboarding.OnboardingScreen
import com.dreamsoftware.nimbustv.ui.screens.profiles.ProfilesScreen
import com.dreamsoftware.nimbustv.ui.screens.splash.SplashScreen
import com.dreamsoftware.nimbustv.ui.utils.navigateSingleTopTo
import com.dreamsoftware.nimbustv.ui.utils.navigationDrawerGraph

@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = "root_host",
        startDestination = Screen.Splash.route,
        modifier = Modifier
            .semantics {
                testTagsAsResourceId = true
            },
        builder = {
            navigationDrawerGraph(
                onNavigateToProfiles = {
                    navController.navigateSingleTopTo(Screen.Profiles.route)
                }
            )
            composable(route = Screen.Splash.route) {
                with(navController) {
                    SplashScreen(
                        onNoProfilesCreated = {
                            navigateSingleTopTo(Screen.Onboarding.route)
                        },
                        onProfileSelectionRequired = {
                            navigateSingleTopTo(Screen.Profiles.route)
                        }
                    )
                }
            }
            composable(route = Screen.Onboarding.route) {
                with(navController) {
                    OnboardingScreen(
                        onGoToHome = {
                            navigateSingleTopTo(Screen.Profiles.route)
                        }
                    )
                }
            }

            composable(route = Screen.Profiles.route) {
                with(navController) {
                    ProfilesScreen(
                        onGoToHome = {
                            navigateSingleTopTo(Screen.Dashboard.route)
                        },
                        onGoToOnboarding = {
                            navigateSingleTopTo(Screen.Onboarding.route)
                        }
                    )
                }
            }
        }
    )
}