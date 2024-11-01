package com.dreamsoftware.nimbustv.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvButton
import com.dreamsoftware.fudge.component.FudgeTvButtonStyleTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvButtonTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvFocusRequester
import com.dreamsoftware.fudge.component.FudgeTvLoadingState
import com.dreamsoftware.fudge.component.FudgeTvNoContentState
import com.dreamsoftware.fudge.component.FudgeTvScreenContent
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.fudge.utils.conditional
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.ui.core.components.ChannelGridItem
import com.dreamsoftware.nimbustv.ui.core.components.CommonLazyVerticalGrid
import com.dreamsoftware.nimbustv.ui.core.components.CommonPopup
import com.dreamsoftware.nimbustv.ui.core.components.MiniKeyboard

@Composable
internal fun SearchScreenContent(
    uiState: SearchUiState,
    actionListener: SearchScreenActionListener
) {
    with(uiState) {
        with(actionListener) {
            channelSelected?.let {
                ChannelDetailsPopup(
                    channel = it,
                    isFavoriteChannel = isFavoriteChannel,
                    onPlayChannel = ::onPlayChannel,
                    onRemoveFromFavorites = ::onRemoveFromFavorites,
                    onAddChannelToFavorites = ::onAddToFavorites,
                    onBackPressed = ::onCloseDetail
                )
            }
            FudgeTvScreenContent(
                error = errorMessage,
                onErrorAccepted = ::onErrorMessageCleared
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(vertical = 12.dp)
                ) {
                    SearchKeyboard(actionListener = actionListener)
                    SearchResults(uiState = uiState, actionListener = actionListener)
                }
            }
        }
    }
}

@Composable
private fun SearchKeyboard(
    actionListener: SearchScreenActionListener
) {
    with(actionListener) {
        Column(modifier = Modifier.padding(horizontal = 12.dp)) {
            FudgeTvText(
                titleRes = R.string.search_screen_main_title,
                type = FudgeTvTextTypeEnum.TITLE_LARGE,
                textColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 8.dp),
            )
            MiniKeyboard(
                modifier = Modifier.width(300.dp),
                onKeyPressed = ::onKeyPressed,
                onSearchPressed = ::onSearchPressed,
                onClearPressed = ::onClearPressed,
                onBackSpacePressed = ::onBackSpacePressed,
                onSpaceBarPressed = ::onSpaceBarPressed
            )
        }
    }
}

@Composable
private fun SearchResults(
    uiState: SearchUiState,
    actionListener: SearchScreenActionListener
) {
    with(uiState) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FudgeTvText(
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 8.dp),
                type = FudgeTvTextTypeEnum.TITLE_LARGE,
                titleText = if(term.isNotBlank()) {
                    stringResource(id = R.string.search_screen_search_results_title_with_term, term)
                } else {
                    stringResource(id = R.string.search_screen_search_results_title_without_term)
                },
                textBold = true
            )
            if (isLoading) {
                FudgeTvLoadingState(modifier = Modifier.fillMaxSize())
            } else if (channels.isEmpty()) {
                FudgeTvNoContentState(
                    modifier = Modifier.fillMaxSize(),
                    messageRes = if(term.isNotEmpty()) {
                        R.string.search_screen_search_no_results_found
                    } else {
                        R.string.search_screen_search_idle
                    }
                )
            } else {
                SearchChannelsGridContent(
                    channels = channels,
                    onChannelSelected = actionListener::onOpenChannelDetail
                )
            }
        }
    }
}

@Composable
private fun SearchChannelsGridContent(
    channels: List<ChannelBO>,
    onChannelSelected: (ChannelBO) -> Unit
) {
    FudgeTvFocusRequester { focusRequester ->
        CommonLazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            state = rememberLazyGridState(),
            items = channels
        ) { idx, item ->
            ChannelGridItem(
                modifier = Modifier.conditional(idx == 0, ifTrue = {
                    focusRequester(focusRequester)
                }),
                channel = item,
                onChannelPressed = onChannelSelected
            )
        }
    }
}

@Composable
private fun ChannelDetailsPopup(
    channel: ChannelBO,
    isFavoriteChannel: Boolean,
    onPlayChannel: (ChannelBO) -> Unit,
    onAddChannelToFavorites: (ChannelBO) -> Unit,
    onRemoveFromFavorites: (ChannelBO) -> Unit,
    onBackPressed: () -> Unit
) {
    with(channel) {
        CommonPopup(
            imageUrl = icon,
            title = title,
            description = category,
            onBackPressed = onBackPressed
        ) { focusRequester ->
            Spacer(modifier = Modifier.weight(1f))
            FudgeTvButton(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                type = FudgeTvButtonTypeEnum.MEDIUM,
                style = FudgeTvButtonStyleTypeEnum.NORMAL,
                textRes = R.string.favorites_screen_channel_detail_popup_open_player_button_text,
                onClick = { onPlayChannel(channel) }
            )
            FudgeTvButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                type = FudgeTvButtonTypeEnum.MEDIUM,
                style = FudgeTvButtonStyleTypeEnum.TRANSPARENT,
                textRes = if(isFavoriteChannel) {
                    R.string.search_screen_channel_detail_popup_remove_from_favorites_button_text
                } else {
                    R.string.search_screen_channel_detail_popup_add_to_favorites_button_text
                },
                onClick = {
                    if(isFavoriteChannel) {
                        onRemoveFromFavorites(channel)
                    } else {
                        onAddChannelToFavorites(channel)
                    }
                }
            )
        }
    }
}