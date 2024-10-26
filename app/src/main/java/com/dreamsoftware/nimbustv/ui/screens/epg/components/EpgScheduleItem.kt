package com.dreamsoftware.nimbustv.ui.screens.epg.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import com.dreamsoftware.nimbustv.domain.model.ProgrammeType
import com.dreamsoftware.nimbustv.ui.screens.epg.extension.toScheduleFormatted
import com.dreamsoftware.nimbustv.ui.screens.epg.model.ScheduleVO

@Composable
fun EpgScheduleItem(
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
    onScheduleClicked: (String) -> Unit
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
                    onClick = { onScheduleClicked(channelId) },
                    headlineContent = {
                        Column {
                            FudgeTvText(
                                titleText = programmeTitle ?: stringResource(id = R.string.epg_screen_channel_not_data_available),
                                type = FudgeTvTextTypeEnum.LABEL_MEDIUM,
                                maxLines = 2,
                                textColor = onPrimaryContainer
                            )
                            if(hasTimeData()) {
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
                    overlineContent = if(fullDetail) {
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
                    leadingContent = if(programmeTypeIconEnabled) {
                        {
                            FudgeTvImageRes(
                                modifier = Modifier.size(ListItemDefaults.IconSizeDense),
                                imageRes = when (type) {
                                    ProgrammeType.LIVE_NOW -> R.drawable.ic_schedule_live_now
                                    ProgrammeType.PAST -> R.drawable.ic_schedule_past
                                    else -> R.drawable.ic_schedule_future
                                },
                                tint = iconColor ?: onPrimary
                            )
                        }
                    } else null,
                    supportingContent = if(fullDetail && progress != null) {
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
                    trailingContent = if(showMoreInfoEnabled && !isSelected) {
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