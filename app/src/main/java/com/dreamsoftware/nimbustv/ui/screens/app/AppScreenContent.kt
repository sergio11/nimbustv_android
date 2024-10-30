package com.dreamsoftware.nimbustv.ui.screens.app

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.dreamsoftware.fudge.component.FudgeTvDialog
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.ui.navigation.AppNavHost
import com.dreamsoftware.nimbustv.ui.theme.LocalNavigationProvider
import com.dreamsoftware.fudge.component.FudgeTvLostNetworkConnectivityDialog
import com.dreamsoftware.fudge.component.FudgeTvScreenContent
import com.dreamsoftware.nimbustv.ui.utils.toTimeFormatted

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
internal fun AppScreenContent(
    uiState: AppUiState,
    navController: NavHostController,
    actionListener: IAppScreenActionListener
) {
    with(uiState) {
        FudgeTvScreenContent(
            error = errorMessage,
            onErrorAccepted = actionListener::onErrorMessageCleared
        ) {
            Box {
                CompositionLocalProvider(LocalNavigationProvider provides navController) {
                    AppNavHost(
                        navController = navController
                    )

                    FudgeTvDialog(
                        isVisible = scheduleReminderFired != null,
                        mainLogoRes = R.drawable.main_logo,
                        titleRes = R.string.reminder_dialog_title_text,
                        description = stringResource(
                            id = R.string.reminder_dialog_description_text,
                            scheduleReminderFired?.title.orEmpty(),
                            scheduleReminderFired?.startTime?.toTimeFormatted().orEmpty(),
                            scheduleReminderFired?.endTime?.toTimeFormatted().orEmpty()
                        ),
                        successRes = R.string.reminder_dialog_accept_button_text,
                        onAcceptClicked = actionListener::onScheduleReminderAccepted,
                    )
                    FudgeTvLostNetworkConnectivityDialog(
                        isVisible = !hasNetworkConnectivity,
                        mainLogoRes = R.drawable.main_logo,
                        onOpenSettings = actionListener::onOpenSettingsPressed,
                        onRestartAppPressed = actionListener::onRestartAppPressed
                    )
                }
            }
        }
    }
}