package com.dreamsoftware.nimbustv.ui.screens.epg

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvButton
import com.dreamsoftware.fudge.component.FudgeTvButtonStyleTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvButtonTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvFocusRequester
import com.dreamsoftware.fudge.component.FudgeTvLoadingState
import com.dreamsoftware.fudge.component.FudgeTvScreenContent
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.fudge.utils.conditional
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.model.EpgBO
import com.dreamsoftware.nimbustv.domain.model.EpgViewModeEnum
import com.dreamsoftware.nimbustv.ui.core.components.CommonPopup
import com.dreamsoftware.nimbustv.ui.core.components.CommonSelectableItem
import com.dreamsoftware.nimbustv.ui.screens.epg.components.EpgChannelOverview
import com.dreamsoftware.nimbustv.ui.screens.epg.components.EpgNowAndSchedule
import com.dreamsoftware.nimbustv.ui.core.components.ImportEpgDataDialog
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
                isImporting = isImporting,
                epgUrl = newEpgDataUrl,
                epgAlias = newEpgAlias,
                onEpgUrlUpdated = ::onNewEpgUrlUpdated,
                onEpgAliasUpdated = ::onNewEpgAliasUpdated,
                onAcceptClicked = ::onImportNewEpgDataConfirmed,
                onCancelClicked = ::onImportNewEpgDataCancelled
            )
            FudgeTvScreenContent(onErrorAccepted = ::onErrorMessageCleared) {
                when {
                    isLoading -> {
                        FudgeTvLoadingState(modifier = Modifier.fillMaxSize())
                    }

                    epgDataIsEmpty -> {
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
        with(actionListener) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                EpgListColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.2f)
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
                        .border(1.dp, MaterialTheme.colorScheme.primary),
                    epgList = epgList,
                    epgSelected = epgSelected,
                    onEpgSelected = ::onNewEpgSelected,
                    onManageEpgListClicked = ::onManageEpgListClicked
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    EpgHeader()
                    if (epgViewMode == EpgViewModeEnum.NOW_AND_SCHEDULE) {
                        EpgNowAndSchedule(
                            liveSchedules = liveSchedules,
                            channelSchedules = currentChannelSchedules,
                            channelSelectedId = channelSelectedId,
                            onOpenEpgChannel = ::onOpenEpgChannel,
                            onOpenScheduleDetail = ::onOpenScheduleDetail
                        )
                    } else {
                        EpgChannelOverview(
                            data = channelOverviewList,
                            onOpenScheduleDetail = ::onOpenScheduleDetail
                        )
                    }
                }
            }
        }
    }
}

/**
 * Composable for the header of the EPG screen.
 */
@Composable
private fun EpgHeader() {
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
    }
}

@Composable
private fun EpgListColumn(
    modifier: Modifier,
    epgList: List<EpgBO>,
    epgSelected: EpgBO? = null,
    onManageEpgListClicked: () -> Unit,
    onEpgSelected: (EpgBO) -> Unit
) {
    FudgeTvFocusRequester(shouldRequestFocus = {
        epgList.isNotEmpty() && epgSelected != null
    }) { requester ->
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.main_logo_inverse),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .padding(top = 16.dp)
            )
            FudgeTvButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .playSoundEffectOnFocus(),
                type = FudgeTvButtonTypeEnum.SMALL,
                style = FudgeTvButtonStyleTypeEnum.TRANSPARENT,
                textRes = R.string.epg_screen_manage_epg_list_button_text,
                onClick = onManageEpgListClicked
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(
                modifier = Modifier.weight(1f, true),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(8.dp)
            ) {
                items(epgList.size) { idx ->
                    val epg = epgList[idx]
                    val isSelected = epg == epgSelected
                    CommonSelectableItem(
                        modifier = Modifier.conditional(condition = isSelected, ifTrue = {
                            focusRequester(requester)
                        }),
                        isSelected = isSelected,
                        titleText = epg.alias,
                        subtitleText = "Channels ( ${epg.channelsCount} )",
                        onItemSelected = { onEpgSelected(epg) }
                    )
                }
            }
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