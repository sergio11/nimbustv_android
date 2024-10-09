package com.dreamsoftware.nimbustv.ui.core.components

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.nimbustv.domain.model.ChannelBO

@OptIn(UnstableApi::class)
@Composable
fun ChannelPreview(
    modifier: Modifier = Modifier,
    channel: ChannelBO
) {
    with(MaterialTheme.colorScheme) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomStart = 16.dp,
                bottomEnd = 0.dp
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            with(channel) {
                Box {
                    CommonVideoBackground(
                        videResource = url,
                        videoResourceLicenseKey = licenseKey
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(primaryContainer.copy(alpha = 0.6f))
                            .padding(vertical = 20.dp, horizontal = 15.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        CommonChannelHeaderInfo(
                            title = title,
                            logo = icon,
                            subtitle = category
                        )
                    }
                }
            }
        }
    }
}