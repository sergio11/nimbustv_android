package com.dreamsoftware.nimbustv.ui.screens.epg.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Icon
import androidx.tv.material3.ListItem
import androidx.tv.material3.ListItemDefaults
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvImageRes
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.model.ScheduleTypeEnum
import com.dreamsoftware.nimbustv.ui.core.components.ChannelLogo
import com.dreamsoftware.nimbustv.ui.screens.epg.extension.toScheduleFormatted
import com.dreamsoftware.nimbustv.ui.screens.epg.model.ScheduleVO

@Composable
internal fun EpgNowAndSchedule(
    liveSchedules: List<ScheduleVO>,
    channelSchedules: List<ScheduleVO>,
    channelSelectedId: String,
    onOpenEpgChannel: (String) -> Unit,
    onOpenScheduleDetail: (ScheduleVO) -> Unit
) {
    with(MaterialTheme.colorScheme) {
        Row {
            EpgSchedulesColumn(schedules = liveSchedules) { schedule ->
                EpgScheduleItem(
                    modifier = Modifier
                        .width(350.dp),
                    isSelected = channelSelectedId == schedule.channelId,
                    schedule = schedule,
                    showMoreInfoEnabled = true,
                    onScheduleClicked = { onOpenEpgChannel(it.channelId) }
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
                        onScheduleClicked = onOpenScheduleDetail
                    )
                }
            }
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


@Composable
private fun EpgScheduleItem(
    modifier: Modifier = Modifier,
    schedule: ScheduleVO,
    isSelected: Boolean = false,
    fullDetail: Boolean = true,
    containerColor: Color? = null,
    focusedContainerColor: Color? = null,
    contentColor: Color? = null,
    focusedContentColor: Color? = null,
    programmeTypeIconEnabled: Boolean = false,
    showMoreInfoEnabled: Boolean = false,
    iconColor: Color? = null,
    onScheduleClicked: (ScheduleVO) -> Unit
) {
    with(MaterialTheme.colorScheme) {
        with(schedule) {
            Box(
                modifier = modifier
                    .background(primaryContainer)
            ) {
                ListItem(
                    selected = isSelected,
                    scale = ListItemDefaults.scale(focusedScale = 1f),
                    shape = ListItemDefaults.shape(shape = RectangleShape),
                    onClick = { onScheduleClicked(schedule) },
                    headlineContent = {
                        Column {
                            FudgeTvText(
                                titleText = programmeTitle
                                    ?: stringResource(id = R.string.epg_screen_channel_not_data_available),
                                type = FudgeTvTextTypeEnum.LABEL_MEDIUM,
                                maxLines = 2,
                                textColor = onPrimaryContainer
                            )
                            if (hasTimeData()) {
                                Spacer(Modifier.width(4.dp))
                                FudgeTvText(
                                    titleText = toScheduleFormatted(),
                                    type = FudgeTvTextTypeEnum.LABEL_SMALL,
                                    textColor = onSurface,
                                    singleLine = true,
                                )
                            }
                        }
                    },
                    overlineContent = if (fullDetail) {
                        {
                            FudgeTvText(
                                titleText = channelName,
                                type = FudgeTvTextTypeEnum.LABEL_LARGE,
                                textBold = true,
                                singleLine = true,
                                textColor = onPrimaryContainer
                            )
                        }
                    } else null,
                    leadingContent = {
                        if (programmeTypeIconEnabled) {
                            FudgeTvImageRes(
                                modifier = Modifier.size(ListItemDefaults.IconSizeDense),
                                imageRes = when (type) {
                                    ScheduleTypeEnum.LIVE_NOW -> R.drawable.ic_schedule_live_now
                                    ScheduleTypeEnum.PAST -> R.drawable.ic_schedule_past
                                    else -> R.drawable.ic_schedule_future
                                },
                                tint = iconColor ?: onPrimary
                            )
                        } else {
                            ChannelLogo(
                                size = ListItemDefaults.IconSize,
                                logo = channelLogoUrl
                            )
                        }
                    },
                    supportingContent = if (fullDetail && progress != null) {
                        {
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { progress / 100f },
                                color = onSecondaryContainer,
                                trackColor = secondaryContainer,
                                gapSize = 0.dp,
                                drawStopIndicator = {},
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                            )
                        }
                    } else {
                        null
                    },
                    trailingContent = if (showMoreInfoEnabled && !isSelected) {
                        {
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                modifier = Modifier.size(ListItemDefaults.IconSizeDense),
                                contentDescription = "Show more info"
                            )
                        }
                    } else null,
                    colors = ListItemDefaults.colors(
                        containerColor = containerColor ?: primaryContainer,
                        focusedContainerColor = focusedContainerColor ?: primary,
                        contentColor = contentColor ?: onPrimaryContainer,
                        focusedContentColor = focusedContentColor ?: onPrimary,
                        selectedContentColor = focusedContentColor ?: onPrimary,
                        selectedContainerColor = focusedContainerColor ?: primary,
                    )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Color.White)
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
}