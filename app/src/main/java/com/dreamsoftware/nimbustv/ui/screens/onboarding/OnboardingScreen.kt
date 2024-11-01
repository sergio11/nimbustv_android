package com.dreamsoftware.nimbustv.ui.screens.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onGoToHome: () -> Unit
) {
    OnboardingScreenContent(
        modifier = modifier,
        onGoToHome = onGoToHome
    )
}