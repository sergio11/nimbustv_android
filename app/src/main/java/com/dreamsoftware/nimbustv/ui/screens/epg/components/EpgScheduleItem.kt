package com.dreamsoftware.nimbustv.ui.screens.epg.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
    schedule: ScheduleVO,
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
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryContainer)
            ) {
                ListItem(
                    selected = false,
                    scale = ListItemDefaults.scale(focusedScale = 1f),
                    shape = ListItemDefaults.shape(shape = RectangleShape),
                    onClick = { onScheduleClicked(channelId) },
                    headlineContent = {
                        FudgeTvText(
                            titleText = programmeTitle ?: stringResource(id = R.string.epg_screen_channel_not_data_available),
                            type = FudgeTvTextTypeEnum.LABEL_MEDIUM,
                            modifier = Modifier.padding(bottom = 8.dp),
                            textColor = onPrimaryContainer
                        )
                    },
                    overlineContent = if(fullDetail) {
                        {
                            FudgeTvText(
                                titleText = channelName,
                                type = FudgeTvTextTypeEnum.LABEL_LARGE,
                                modifier = Modifier.padding(bottom = 8.dp),
                                textBold = true,
                                textColor = onPrimaryContainer
                            )
                        }
                    } else null,
                    leadingContent = if(programmeTypeIconEnabled) {
                        {
                            FudgeTvImageRes(
                                modifier = Modifier.size(15.dp),
                                imageRes = when (type) {
                                    ProgrammeType.LIVE_NOW -> R.drawable.ic_schedule_live_now
                                    ProgrammeType.PAST -> R.drawable.ic_schedule_past
                                    else -> R.drawable.ic_schedule_future
                                },
                                tint = iconColor ?: onPrimary
                            )
                        }
                    } else null,
                    supportingContent = if(hasTimeData()) {
                        {
                            Column {
                                FudgeTvText(
                                    titleText = toScheduleFormatted(),
                                    type = FudgeTvTextTypeEnum.LABEL_SMALL,
                                    textColor = onSurface.copy(alpha = 0.7f),
                                )
                                if(fullDetail && progress != null) {
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
                            }
                        }
                    } else {
                        null
                    },
                    trailingContent = if(showMoreInfoEnabled) {
                        {
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                modifier = Modifier.size(ListItemDefaults.IconSize),
                                contentDescription = "Show more info"
                            )
                        }
                    } else null,
                    colors = ListItemDefaults.colors(
                        containerColor = containerColor ?: primaryContainer,
                        focusedContainerColor = focusedContainerColor ?: primary,
                        contentColor = contentColor ?: onPrimaryContainer,
                        focusedContentColor = focusedContentColor ?: onPrimary
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