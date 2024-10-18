package com.dreamsoftware.nimbustv.ui.screens.epg

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvButton
import com.dreamsoftware.fudge.component.FudgeTvButtonStyleTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvButtonTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvDialog
import com.dreamsoftware.fudge.component.FudgeTvFocusRequester
import com.dreamsoftware.fudge.component.FudgeTvLoadingState
import com.dreamsoftware.fudge.component.FudgeTvScreenContent
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.fudge.utils.conditional
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.model.EpgDataBO
import com.dreamsoftware.nimbustv.domain.model.ProgrammeDataBO
import com.dreamsoftware.nimbustv.ui.screens.epg.components.EpgProgrammeCell
import com.dreamsoftware.nimbustv.ui.screens.epg.components.ImportEpgDataDialog
import com.dreamsoftware.nimbustv.ui.screens.epg.components.NoEpgDataFound
import com.dreamsoftware.nimbustv.ui.screens.onboarding.playSoundEffectOnFocus
import com.dreamsoftware.nimbustv.ui.theme.onPrimary
import com.dreamsoftware.nimbustv.ui.utils.toScheduleFormatted

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
                    epgData.isEmpty() -> {
                        NoEpgDataFound(onImportClicked = ::onImportNewEpgData)
                    }
                    else -> {
                        EpgMainContent(epgData, actionListener)
                    }
                }
            }
        }
    }
}

/**
 * Composable to display the main EPG content including title and list of channels.
 */
@Composable
private fun EpgMainContent(
    epgData: List<EpgDataBO>,
    actionListener: EpgScreenActionListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        EpgHeader(actionListener)
        EpgList(epgData)
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

/**
 * Composable to display a list of channels and their programs.
 */
@Composable
private fun EpgList(epgData: List<EpgDataBO>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        itemsIndexed(epgData) { idx, channelData ->
            ChannelItem(
                channelData = channelData,
                isFirstChannel = idx == 0
            )
        }
    }
}

/**
 * Composable to display an individual channel and its programs.
 */
@Composable
private fun ChannelItem(
    channelData: EpgDataBO,
    isFirstChannel: Boolean
) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clip(RoundedCornerShape(8.dp))
            .shadow(elevation = 4.dp) // Shadow for a lifted effect
            .padding(16.dp)
    ) {
        FudgeTvText(
            titleText = channelData.displayName,
            type = FudgeTvTextTypeEnum.BODY_LARGE,
            modifier = Modifier.padding(bottom = 8.dp),
            textBold = true,
            textColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
        ProgramList(
            programmeList = channelData.programmeList,
            isFirstChannel = isFirstChannel
        )
    }
}

/**
 * Composable to display a horizontal list of programs under a channel.
 * If the programmeList is empty, a message indicating no information is displayed.
 */
@Composable
private fun ProgramList(
    programmeList: List<ProgrammeDataBO>,
    isFirstChannel: Boolean
) {
    if (programmeList.isEmpty()) {
        // Show a card with the message if there are no programs
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(100.dp),
            contentAlignment = Alignment.Center
        ) {
            EpgProgrammeCell {
                FudgeTvText(
                    modifier = Modifier.padding(16.dp),
                    titleRes = R.string.epg_screen_channel_not_data_available,
                    type = FudgeTvTextTypeEnum.BODY_MEDIUM,
                    textColor = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        // Display the list of programs in a horizontal row
        FudgeTvFocusRequester { focusRequester ->
            LazyRow(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(programmeList) { idx, programme ->
                    ProgrammeItem(
                        modifier = Modifier.conditional(isFirstChannel && idx == 0, ifTrue = {
                            focusRequester(focusRequester)
                        }),
                        programme = programme
                    )
                }
            }
        }
    }
}

/**
 * Composable to display an individual program.
 */
@Composable
private fun ProgrammeItem(
    modifier: Modifier = Modifier,
    programme: ProgrammeDataBO
) {
    EpgProgrammeCell(
        modifier = modifier
            .width(200.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            FudgeTvText(
                titleText = programme.title,
                type = FudgeTvTextTypeEnum.BODY_MEDIUM,
                textColor = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(4.dp))
            FudgeTvText(
                titleText = programme.toScheduleFormatted(),
                type = FudgeTvTextTypeEnum.BODY_MEDIUM,
                textColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            )
        }
    }
}
