package com.dreamsoftware.nimbustv.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dreamsoftware.nimbustv.ui.screens.profiles.changesecurepin.ChangeSecurePinScreen
import com.dreamsoftware.nimbustv.ui.screens.profiles.delete.DeleteProfileScreen
import com.dreamsoftware.nimbustv.ui.screens.profiles.management.ProfilesManagementScreen
import com.dreamsoftware.nimbustv.ui.screens.profiles.save.SaveProfileScreen
import com.dreamsoftware.nimbustv.ui.screens.profiles.secure.SecurePinScreen
import com.dreamsoftware.nimbustv.ui.screens.profiles.selector.ProfileSelectorScreen
import com.dreamsoftware.nimbustv.ui.utils.navigateSingleTopTo

@Composable
fun ProfilesNavigation(
    navController: NavHostController,
    onGoToOnboarding: () -> Unit,
    onGoToHome: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ProfileSelector.route
    ) {
        composable(Screen.ProfileSelector.route) {
            with(navController) {
                ProfileSelectorScreen(
                    onProfileLocked = {
                        navigate(Screen.UnlockProfile.buildRoute(it))
                    },
                    onGoToAddProfile = {
                        navigate(Screen.AddProfile.route)
                    },
                    onCancelProfileSelection = {
                        onGoToOnboarding()
                    },
                    onGoToProfileManagement = {
                        navigate(Screen.ProfilesManagement.route)
                    }
                )
            }
        }

        composable(Screen.AddProfile.route) {
            SaveProfileScreen(
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }

        composable(Screen.EditProfile.route) { navBackStackEntry ->
            navBackStackEntry.arguments?.let { args ->
                Screen.EditProfile.parseArgs(args)?.let {
                    with(navController) {
                        SaveProfileScreen(
                            args = it,
                            onGoToAdvanceConfiguration = {
                                navigate(Screen.ProfileAdvance.buildRoute(it))
                            },
                            onBackPressed = {
                                navigateUp()
                            },
                        )
                    }
                }
            }
        }

        composable(Screen.UnlockProfile.route) { navBackStackEntry ->
            navBackStackEntry.arguments?.let { args ->
                Screen.UnlockProfile.parseArgs(args)?.let {
                    SecurePinScreen(
                        args = it,
                        onGoToHome = onGoToHome,
                        onBackPressed = {
                            navController.navigateUp()
                        },
                    )
                }
            }
        }

        composable(Screen.ProfilesManagement.route) {
            with(navController) {
                ProfilesManagementScreen(
                    onGoToEditProfile = {
                        navigate(Screen.EditProfile.buildRoute(it))
                    },
                    onBackPressed = {
                        popBackStack()
                    }
                )
            }
        }

        composable(Screen.ProfileAdvance.route) { navBackStackEntry ->
            navBackStackEntry.arguments?.let { args ->
                Screen.ProfileAdvance.parseArgs(args)?.let {
                    with(navController) {
                        ChangeSecurePinScreen(
                            args = it,
                            onGoToDeleteProfile = {
                                navigate(Screen.DeleteProfile.buildRoute(it))
                            },
                            onBackPressed = {
                                popBackStack()
                            }
                        )
                    }
                }
            }
        }

        composable(Screen.DeleteProfile.route) { navBackStackEntry ->
            navBackStackEntry.arguments?.let { args ->
                Screen.DeleteProfile.parseArgs(args)?.let {
                    with(navController) {
                        DeleteProfileScreen(
                            args = it,
                            onProfileDeletedSuccessfully = {
                                navigateSingleTopTo(Screen.ProfileSelector.route)
                            },
                            onBackPressed = {
                                popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}