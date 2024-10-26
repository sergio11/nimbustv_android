package com.dreamsoftware.nimbustv.ui.screens.epg.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
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