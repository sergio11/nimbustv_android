package com.dreamsoftware.nimbustv.ui.screens.profiles.delete

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.ui.theme.Dimens
import com.dreamsoftware.nimbustv.ui.utils.toDrawableResource
import com.dreamsoftware.fudge.component.FudgeTvDialog
import com.dreamsoftware.fudge.component.FudgeTvFocusRequester
import com.dreamsoftware.fudge.component.FudgeTvScalableAvatar
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.fudge.component.profiles.FudgeTvProfileScreenContent

@Composable
internal fun DeleteProfileScreenContent(
    uiState: DeleteProfileUiState,
    actionListener: DeleteProfileScreenActionListener
) {
    with(uiState) {
        FudgeTvProfileScreenContent(
            isLoading = isLoading,
            error = errorMessage,
            mainLogoRes = R.drawable.main_logo,
            mainLogoInverseRes = R.drawable.main_logo_inverse,
            loadingTitleRes = R.string.generic_progress_dialog_title,
            loadingDescriptionRes = R.string.generic_progress_dialog_description,
            mainTitleRes = R.string.delete_profile_main_title,
            secondaryTitleRes = R.string.delete_profile_main_description,
            primaryOptionTextRes = R.string.delete_profile_form_accept_button_text,
            secondaryOptionTextRes = R.string.delete_profile_form_cancel_button_text,
            onPrimaryOptionPressed = actionListener::onDeletePressed,
            onSecondaryOptionPressed = actionListener::onCancelPressed,
            onErrorAccepted = actionListener::onErrorMessageCleared
        ) {
            FudgeTvDialog(
                isVisible = showProfileDeletedDialog,
                mainLogoRes = R.drawable.main_logo,
                titleRes = R.string.delete_profile_dialog_title,
                descriptionRes = R.string.delete_profile_dialog_description,
                onAcceptClicked = actionListener::onProfileDeletedDialogClosed,
            )
            FudgeTvFocusRequester {
                FudgeTvScalableAvatar(
                    modifier = Modifier.focusRequester(it),
                    avatarRes = profile?.avatarType?.toDrawableResource(),
                    padding = Dimens.PROFILE_AVATAR_NO_PADDING
                )
            }
            FudgeTvText(
                titleText = profile?.alias.orEmpty(),
                type = FudgeTvTextTypeEnum.TITLE_LARGE,
                textBold = true
            )
            FudgeTvText(
                modifier = Modifier.padding(horizontal = 30.dp),
                titleRes = R.string.delete_profile_explanation_text,
                type = FudgeTvTextTypeEnum.BODY_LARGE,
                textAlign = TextAlign.Center
            )
        }
    }
}