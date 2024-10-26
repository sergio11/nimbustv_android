package com.dreamsoftware.nimbustv.ui.screens.epg

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.dreamsoftware.fudge.component.FudgeTvButton
import com.dreamsoftware.fudge.component.FudgeTvButtonStyleTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvButtonTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvDialog
import com.dreamsoftware.fudge.component.FudgeTvLoadingState
import com.dreamsoftware.fudge.component.FudgeTvScreenContent
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.ui.core.components.CommonPopup
import com.dreamsoftware.nimbustv.ui.screens.epg.components.EpgNowAndSchedule
import com.dreamsoftware.nimbustv.ui.screens.epg.components.ImportEpgDataDialog
import com.dreamsoftware.nimbustv.ui.screens.epg.components.NoEpgDataFound
import com.dreamsoftware.nimbustv.ui.screens.epg.model.ScheduleVO
import com.dreamsoftware.nimbustv.ui.screens.onboarding.playSoundEffectOnFocus
import com.dreamsoftware.nimbustv.ui.theme.onPrimary

@Composable
internal fun EpgScreenContent(
    uiState: EpgUiState,
    actionListener: EpgScreenActionListener
) {
    with(uiState) {
        with(actionListener) {
            ImportEpgDataDialog(
                isVisible = showImportEpgDataDialog,
                epgDataUrl = newEpgDataUrl,
                onEpgDataUrlUpdated = ::onNewEpgDataUrlUpdated,
                onAcceptClicked = ::onImportNewEpgDataConfirmed,
                onCancelClicked = ::onImportNewEpgDataCancelled
            )
            FudgeTvDialog(
                isVisible = showRemoveEpgDataDialog,
                mainLogoRes = R.drawable.main_logo,
                titleRes = R.string.epg_screen_remove_epg_data_dialog_title,
                descriptionRes = R.string.epg_screen_remove_epg_data_dialog_description,
                successRes = R.string.epg_screen_remove_epg_data_dialog_accept_button,
                cancelRes = R.string.epg_screen_remove_epg_data_dialog_cancel_button,
                onAcceptClicked = ::onRemoveEpgDataConfirmed,
                onCancelClicked = ::onRemoveEpgDataCancelled
            )
            FudgeTvScreenContent(onErrorAccepted = ::onErrorMessageCleared) {
                when {
                    isLoading -> {
                        FudgeTvLoadingState(modifier = Modifier.fillMaxSize())
                    }

                    liveSchedules.isEmpty() -> {
                        NoEpgDataFound(onImportClicked = ::onImportNewEpgData)
                    }
                    else -> {
                        scheduleSelected?.let {
                            ScheduleDetailsPopup(
                                schedule = it,
                                onBackPressed = actionListener::onCloseScheduleDetail
                            )
                        }
                        EpgMainContent(
                            uiState = uiState,
                            actionListener = actionListener
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun EpgMainContent(
    uiState: EpgUiState,
    actionListener: EpgScreenActionListener
) {
    with(uiState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            EpgHeader(actionListener)
            EpgNowAndSchedule(
                liveSchedules = liveSchedules,
                channelSchedules = currentChannelSchedules,
                channelSelectedId = channelSelectedId,
                onOpenEpgChannel = actionListener::onOpenEpgChannel,
                onOpenScheduleDetail = actionListener::onOpenScheduleDetail
            )
        }
    }
}

/**
 * Composable for the header of the EPG screen.
 */
@Composable
private fun EpgHeader(actionListener: EpgScreenActionListener) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FudgeTvText(
            modifier = Modifier.padding(vertical = 16.dp),
            type = FudgeTvTextTypeEnum.TITLE_LARGE,
            titleRes = R.string.epg_screen_title,
            textColor = onPrimary
        )
        Row {
            FudgeTvButton(
                modifier = Modifier
                    .width(150.dp)
                    .playSoundEffectOnFocus(),
                type = FudgeTvButtonTypeEnum.SMALL,
                style = FudgeTvButtonStyleTypeEnum.TRANSPARENT,
                textRes = R.string.epg_screen_import_new_button_text,
                onClick = actionListener::onImportNewEpgData
            )
            Spacer(modifier = Modifier.width(10.dp))
            FudgeTvButton(
                modifier = Modifier
                    .width(150.dp)
                    .playSoundEffectOnFocus(),
                type = FudgeTvButtonTypeEnum.SMALL,
                style = FudgeTvButtonStyleTypeEnum.INVERSE,
                textRes = R.string.epg_screen_remove_button_text,
                onClick = actionListener::onRemoveEpgData
            )
        }
    }
}


@Composable
private fun ScheduleDetailsPopup(
    schedule: ScheduleVO,
    onBackPressed: () -> Unit
) {
    with(schedule) {
        CommonPopup(
            imageUrl = channelLogoUrl,
            title = programmeTitle,
            description = programmeDescription,
            onBackPressed = onBackPressed
        ) { focusRequester ->
            Spacer(modifier = Modifier.weight(1f))
            FudgeTvButton(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                type = FudgeTvButtonTypeEnum.MEDIUM,
                style = FudgeTvButtonStyleTypeEnum.NORMAL,
                textRes = R.string.favorites_screen_channel_detail_popup_open_player_button_text,
                onClick = { }
            )
        }
    }
}