package com.dreamsoftware.nimbustv.ui.screens.profiles

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.dreamsoftware.nimbustv.ui.navigation.ProfilesNavigation

@Composable
fun ProfilesScreen(
    onGoToHome: () -> Unit,
    onGoToOnboarding: () -> Unit
) {
    val navController = rememberNavController()
    ProfilesNavigation(
        navController = navController,
        onGoToHome = onGoToHome,
        onGoToOnboarding = onGoToOnboarding
    )
}