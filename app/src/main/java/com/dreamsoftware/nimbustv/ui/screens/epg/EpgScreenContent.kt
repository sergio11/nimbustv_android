package com.dreamsoftware.nimbustv.ui.screens.epg

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvButton
import com.dreamsoftware.fudge.component.FudgeTvButtonStyleTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvButtonTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvDialog
import com.dreamsoftware.fudge.component.FudgeTvLoadingState
import com.dreamsoftware.fudge.component.FudgeTvScreenContent
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.ui.screens.epg.components.EpgScheduleItem
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
                        EpgMainContent(
                            liveSchedules = liveSchedules,
                            channelSchedules = currentChannelSchedules,
                            channelSelectedId = channelSelectedId,
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
    liveSchedules: List<ScheduleVO>,
    channelSchedules: List<ScheduleVO>,
    channelSelectedId: String,
    actionListener: EpgScreenActionListener
) {
    with(MaterialTheme.colorScheme) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            EpgHeader(actionListener)
            Row {
                EpgSchedulesColumn(schedules = liveSchedules) { schedule ->
                    EpgScheduleItem(
                        modifier = Modifier
                            .width(350.dp),
                        isSelected = channelSelectedId == schedule.channelId,
                        schedule = schedule,
                        showMoreInfoEnabled = true,
                        onScheduleClicked = actionListener::onOpenEpgChannel
                    )
                }
                if(channelSchedules.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(2.dp))
                    EpgSchedulesColumn(
                        schedules = channelSchedules,
                        focusItemIndex = channelSchedules.indexOfFirst { it.isLiveNow() }.takeIf { it >= 0 }
                    ) { schedule ->
                        EpgScheduleItem(
                            modifier = Modifier
                                .width(300.dp),
                            schedule = schedule,
                            isSelected = schedule.isLiveNow(),
                            fullDetail = false,
                            programmeTypeIconEnabled = true,
                            containerColor = surface,
                            focusedContainerColor = surfaceVariant,
                            contentColor = onSurface,
                            focusedContentColor = onSurfaceVariant,
                            onScheduleClicked = actionListener::onOpenEpgChannel
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
private fun EpgSchedulesColumn(
    modifier: Modifier = Modifier,
    schedules: List<ScheduleVO>,
    focusItemIndex: Int? = null,
    onBuildScheduleItem: @Composable BoxScope.(ScheduleVO) -> Unit
) {
    with(MaterialTheme.colorScheme) {
        val listState = rememberLazyListState()
        var containerHeight by remember { mutableIntStateOf(0) }
        var itemHeight by remember { mutableIntStateOf(0) }
        LaunchedEffect(focusItemIndex) {
            if (focusItemIndex != null && itemHeight > 0) {
                val offset = (containerHeight - itemHeight) / 2
                listState.animateScrollToItem(focusItemIndex, scrollOffset = -offset)
            }
        }
        LazyColumn(
            modifier = modifier
                .onSizeChanged { size -> containerHeight = size.height }
                .background(primaryContainer),
            state = listState
        ) {
            items(schedules) { schedule ->
                Box(
                    modifier = Modifier
                        .onSizeChanged { size -> itemHeight = size.height }
                ) {
                    onBuildScheduleItem(schedule)
                }
            }
        }
    }
}