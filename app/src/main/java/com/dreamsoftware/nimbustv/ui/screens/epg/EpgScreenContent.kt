package com.dreamsoftware.nimbustv.ui.screens.epg

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvLoadingState
import com.dreamsoftware.fudge.component.FudgeTvNoContentState
import com.dreamsoftware.fudge.component.FudgeTvScreenContent
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.model.EpgDataBO
import com.dreamsoftware.nimbustv.domain.model.ProgrammeDataBO
import com.dreamsoftware.nimbustv.ui.screens.epg.components.EpgProgrammeCell
import com.dreamsoftware.nimbustv.ui.theme.onPrimary
import com.dreamsoftware.nimbustv.ui.utils.toScheduleFormatted

@Composable
internal fun EpgScreenContent(
    uiState: EpgUiState,
    actionListener: EpgScreenActionListener
) {
    with(uiState) {
        FudgeTvScreenContent(onErrorAccepted = actionListener::onErrorMessageCleared) {
            // Loading state
            when {
                isLoading -> {
                    FudgeTvLoadingState(modifier = Modifier.fillMaxSize())
                }
                epgData.isEmpty() -> { // Assuming `epgData` is a List<EpgDataBO>
                    FudgeTvNoContentState(
                        modifier = Modifier.fillMaxSize(),
                        messageRes = R.string.favorites_not_workout_available
                    )
                }
                else -> {
                    EpgMainContent(epgData)
                }
            }
        }
    }
}

/**
 * Composable to display the main EPG content including title and list of channels.
 */
@Composable
fun EpgMainContent(epgData: List<EpgDataBO>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        EpgTitle()
        EpgList(epgData)
    }
}

/**
 * Composable for the title of the EPG screen.
 */
@Composable
fun EpgTitle() {
    FudgeTvText(
        modifier = Modifier.padding(vertical = 16.dp),
        type = FudgeTvTextTypeEnum.TITLE_LARGE,
        titleRes = R.string.epg_screen_title,
        textColor = onPrimary
    )
}

/**
 * Composable to display a list of channels and their programs.
 */
@Composable
fun EpgList(epgData: List<EpgDataBO>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(epgData) { channelData ->
            ChannelItem(channelData)
        }
    }
}

/**
 * Composable to display an individual channel and its programs.
 */
@Composable
fun ChannelItem(channelData: EpgDataBO) {
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
        ProgramList(channelData.programmeList)
    }
}

/**
 * Composable to display a horizontal list of programs under a channel.
 * If the programmeList is empty, a message indicating no information is displayed.
 */
@Composable
fun ProgramList(programmeList: List<ProgrammeDataBO>) {
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
        LazyRow(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(programmeList) { programme ->
                ProgrammeItem(programme)
            }
        }
    }
}

/**
 * Composable to display an individual program.
 */
@Composable
fun ProgrammeItem(programme: ProgrammeDataBO) {
    EpgProgrammeCell(
        modifier = Modifier
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
