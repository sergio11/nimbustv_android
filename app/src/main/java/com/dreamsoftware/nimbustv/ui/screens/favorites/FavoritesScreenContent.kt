package com.dreamsoftware.nimbustv.ui.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dreamsoftware.fudge.component.FudgeTvFocusRequester
import com.dreamsoftware.fudge.component.FudgeTvLoadingState
import com.dreamsoftware.fudge.component.FudgeTvNoContentState
import com.dreamsoftware.fudge.component.FudgeTvScreenContent
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.ui.core.components.ChannelGridItem
import com.dreamsoftware.nimbustv.ui.core.components.CommonLazyVerticalGrid

@Composable
internal fun FavoritesScreenContent(
    uiState: FavoritesUiState,
    actionListener: FavoritesScreenActionListener
) {
    with(uiState) {
        FudgeTvScreenContent(onErrorAccepted = actionListener::onErrorMessageCleared) {
            if (isLoading) {
                FudgeTvLoadingState(modifier = Modifier.fillMaxSize())
            } else if (channels.isEmpty()) {
                FudgeTvNoContentState(
                    modifier = Modifier.fillMaxSize(),
                    messageRes = R.string.favorites_screen_no_channels_found_text
                )
            } else {
                FavoriteChannelsMainContent(
                    channels = channels,
                    actionListener = actionListener
                )
            }
        }
    }
}

@Composable
private fun FavoriteChannelsMainContent(
    channels: List<ChannelBO>,
    actionListener: FavoritesScreenActionListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        FavoriteChannelsHeader(actionListener = actionListener)
        FavoriteChannelsGridContent(
            channels = channels,
            onChannelSelected = { }
        )
    }
}

@Composable
private fun FavoriteChannelsHeader(actionListener: FavoritesScreenActionListener) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .padding(end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FudgeTvText(
            type = FudgeTvTextTypeEnum.HEADLINE_MEDIUM,
            titleRes = R.string.favorites_screen_title,
            textBold = true
        )
    }
}

@Composable
private fun FavoriteChannelsGridContent(
    channels: List<ChannelBO>,
    onChannelSelected: (ChannelBO) -> Unit
) {
    FudgeTvFocusRequester { focusRequester ->
        CommonLazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            state = rememberLazyGridState(),
            items = channels
        ) { item ->
            ChannelGridItem(
                channel = item,
                onChannelPressed = onChannelSelected
            )
        }
    }
}