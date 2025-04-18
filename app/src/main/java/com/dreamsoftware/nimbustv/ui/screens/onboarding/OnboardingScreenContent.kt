package com.dreamsoftware.nimbustv.ui.screens.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvButton
import com.dreamsoftware.fudge.component.FudgeTvButtonTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvFocusRequester
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvVideoBackground
import com.dreamsoftware.fudge.component.fudgeTvPlaySoundEffectOnFocus
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.ui.core.components.ExitAppDialog

@Composable
internal fun OnboardingScreenContent(
    modifier: Modifier = Modifier,
    onGoToHome: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        OnboardingVideoBackground()
        ConfirmExitAppDialog()
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.9f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
            ) {
                val commonModifier = Modifier
                    .fillMaxSize()
                    .weight(0.8f)
                OnBoardingLogo(modifier = commonModifier)
                OnboardingContentInfo(modifier = commonModifier)
            }
            OnBoardingBottom(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onGoToHome = onGoToHome
            )
        }
    }
}

@Composable
private fun ConfirmExitAppDialog() {
    var confirmExitApp by remember { mutableStateOf(false) }
    BackHandler { confirmExitApp = true }
    ExitAppDialog(
        isVisible = confirmExitApp,
        onDismissPressed = {
            confirmExitApp = false
        },
        onExitPressed = {
            confirmExitApp = false
        }
    )
}

@Composable
private fun OnBoardingLogo(modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.main_logo_inverse),
            contentDescription = null,
            modifier = Modifier
                .height(250.dp)
                .align(Alignment.TopCenter)
                .padding(top = 60.dp)
        )
    }
}

@Composable
private fun OnboardingContentInfo(
    modifier: Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            FudgeTvText(
                titleRes = R.string.onboarding_main_title_text,
                type = FudgeTvTextTypeEnum.HEADLINE_LARGE,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp),
                textBold = true
            )
            Spacer(modifier = Modifier.height(20.dp))
            FudgeTvText(
                titleRes = R.string.onboarding_secondary_title_text,
                type = FudgeTvTextTypeEnum.BODY_LARGE,
                textAlign = TextAlign.Center,
                textBold = true
            )
            Spacer(modifier = Modifier.height(50.dp))
            FudgeTvText(
                titleRes = R.string.onboarding_additional_info_text,
                type = FudgeTvTextTypeEnum.HEADLINE_SMALL,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun OnboardingVideoBackground() {
    FudgeTvVideoBackground(videoResourceId = R.raw.onboarding_video)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
    )
}

@Composable
private fun OnBoardingBottom(
    modifier: Modifier,
    onGoToHome: () -> Unit
) {
    FudgeTvFocusRequester { requester ->
        Row (
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            val commonModifier = Modifier
                .weight(0.65f)
            FudgeTvText(
                modifier = commonModifier,
                titleRes = R.string.developer_credits_text,
                type = FudgeTvTextTypeEnum.LABEL_MEDIUM,
                textAlign = TextAlign.Center
            )
            Box(modifier = commonModifier) {
                FudgeTvButton(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fudgeTvPlaySoundEffectOnFocus()
                        .focusRequester(requester),
                    type = FudgeTvButtonTypeEnum.LARGE,
                    textRes = R.string.onboarding_lets_go_button_text,
                    onClick = onGoToHome,
                )
            }
        }
    }

}