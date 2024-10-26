package com.dreamsoftware.nimbustv.ui.screens.epg.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvFocusRequester
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.fudge.utils.conditional
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.ui.screens.epg.extension.toScheduleFormatted
import com.dreamsoftware.nimbustv.ui.screens.epg.model.ChannelOverviewVO
import com.dreamsoftware.nimbustv.ui.screens.epg.model.ScheduleVO

@Composable
internal fun EpgChannelOverview(
    data: List<ChannelOverviewVO>,
    onOpenScheduleDetail: (ScheduleVO) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        itemsIndexed(data) { idx, channelData ->
            ChannelOverviewItem(
                channelOverview = channelData,
                onOpenScheduleDetail = onOpenScheduleDetail,
                isFirstChannel = idx == 0
            )
        }
    }
}

/**
 * Composable to display an individual channel and its programs.
 */
@Composable
private fun ChannelOverviewItem(
    channelOverview: ChannelOverviewVO,
    onOpenScheduleDetail: (ScheduleVO) -> Unit,
    isFirstChannel: Boolean
) {
    with(channelOverview) {
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
                titleText = title,
                type = FudgeTvTextTypeEnum.BODY_LARGE,
                modifier = Modifier.padding(bottom = 8.dp),
                textBold = true,
                textColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
            ChannelScheduleList(
                scheduleList = schedules,
                onOpenScheduleDetail = onOpenScheduleDetail,
                isFirstChannel = isFirstChannel
            )
        }
    }
}

/**
 * Composable to display a horizontal list of programs under a channel.
 * If the programmeList is empty, a message indicating no information is displayed.
 */
@Composable
private fun ChannelScheduleList(
    scheduleList: List<ScheduleVO>,
    onOpenScheduleDetail: (ScheduleVO) -> Unit,
    isFirstChannel: Boolean
) {
    if (scheduleList.isEmpty()) {
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
                itemsIndexed(scheduleList) { idx, schedule ->
                    ChannelScheduleItem(
                        modifier = Modifier.conditional(isFirstChannel && idx == 0, ifTrue = {
                            focusRequester(focusRequester)
                        }),
                        onOpenScheduleDetail = onOpenScheduleDetail,
                        schedule = schedule
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
private fun ChannelScheduleItem(
    modifier: Modifier = Modifier,
    onOpenScheduleDetail: (ScheduleVO) -> Unit,
    schedule: ScheduleVO
) {
    with(schedule) {
        EpgProgrammeCell(
            modifier = modifier
                .width(200.dp),
            onClick = { onOpenScheduleDetail(schedule) }
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                FudgeTvText(
                    titleText = programmeTitle,
                    type = FudgeTvTextTypeEnum.BODY_MEDIUM,
                    textColor = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(4.dp))
                FudgeTvText(
                    titleText = toScheduleFormatted(),
                    type = FudgeTvTextTypeEnum.BODY_MEDIUM,
                    textColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                )
            }
        }
    }
}

@Composable
private fun EpgProgrammeCell(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit) ? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .padding(4.dp),
        border = CardDefaults.border(
            focusedBorder =
            Border(
                border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.onPrimaryContainer),
                shape = RoundedCornerShape(8.dp)
            )
        ),
        colors = CardDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = {
            onClick?.invoke()
        }
    ) {
        content()
    }
}