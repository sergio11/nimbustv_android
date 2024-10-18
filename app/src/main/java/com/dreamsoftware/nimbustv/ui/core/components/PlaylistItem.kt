package com.dreamsoftware.nimbustv.ui.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.nimbustv.domain.model.PlayListBO

@Composable
fun PlaylistItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    playlist: PlayListBO,
    onPlaylistSelected: (PlayListBO) -> Unit
) {
    with(MaterialTheme.colorScheme) {
        CommonListItem(modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp),
            focusedBorderColor = primary,
            borderColor = primaryContainer,
            isSelected = isSelected,
            onClicked = {
                onPlaylistSelected(playlist)
            }
        ) { isFocused ->
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val textColor = with(MaterialTheme.colorScheme) {
                    if (isFocused || isSelected) {
                        primary
                    } else {
                        onPrimaryContainer
                    }
                }
                FudgeTvText(
                    type = FudgeTvTextTypeEnum.BODY_LARGE,
                    titleText = playlist.alias,
                    textAlign = TextAlign.Center,
                    textBold = true,
                    maxLines = 2,
                    textColor = textColor
                )
                FudgeTvText(
                    type = FudgeTvTextTypeEnum.BODY_SMALL,
                    titleText = "Channels ( ${playlist.channelsCount} )",
                    textAlign = TextAlign.Center,
                    singleLine = true,
                    textColor = textColor
                )
            }
        }
    }
}