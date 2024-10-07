package com.dreamsoftware.nimbustv.ui.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.nimbustv.domain.model.ChannelBO

@Composable
fun ChannelGridItem(
    modifier: Modifier = Modifier,
    channel: ChannelBO,
    onChannelFocused: (ChannelBO) -> Unit = {},
    onChannelPressed: (ChannelBO) -> Unit = {},
) {
    with(MaterialTheme.colorScheme) {
        CommonListItem(
            modifier = modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(8.dp),
            containerColor = primaryContainer.copy(0.7f),
            borderColor = onPrimary,
            onFocused = { onChannelFocused(channel) },
            onClicked = { onChannelPressed(channel) }
        ) { isFocused ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ChannelLogo(
                    size = 80.dp,
                    logo = channel.icon,
                    borderColor = if(isFocused) {
                        primary
                    } else {
                        onPrimaryContainer
                    }
                )
                FudgeTvText(
                    type = FudgeTvTextTypeEnum.BODY_MEDIUM,
                    titleText = channel.title,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    textColor = if(isFocused) {
                        primary
                    } else {
                        onPrimaryContainer
                    }
                )
            }
        }
    }
}