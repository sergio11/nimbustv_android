package com.dreamsoftware.nimbustv.ui.screens.playlistdetail

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import com.dreamsoftware.fudge.component.FudgeTvButton
import com.dreamsoftware.fudge.component.FudgeTvButtonStyleTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvButtonTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvDialog
import com.dreamsoftware.fudge.component.FudgeTvFocusRequester
import com.dreamsoftware.fudge.component.FudgeTvLazyVerticalGrid
import com.dreamsoftware.fudge.component.FudgeTvLoadingState
import com.dreamsoftware.fudge.component.FudgeTvNoContentState
import com.dreamsoftware.fudge.component.FudgeTvPopupDialog
import com.dreamsoftware.fudge.component.FudgeTvScreenContent
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.fudge.component.fudgeTvPlaySoundEffectOnFocus
import com.dreamsoftware.fudge.utils.conditional
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.ui.core.components.ChannelGridItem

@Composable
internal fun PlaylistDetailScreenContent(
    uiState: PlaylistDetailUiState,
    actionListener: PlaylistDetailScreenActionListener
) {
    with(uiState) {
        with(actionListener) {
            channelSelected?.let {
                PlaylistChannelDetailsPopup(
                    channel = it,
                    onPlayChannel = ::onPlayChannel,
                    onRemoveFromPlaylist = ::onRemoveFromPlaylist,
                    onBackPressed = ::onCloseChannelDetail
                )
            }
            FudgeTvDialog(
                isVisible = showDeletePlaylistDialog,
                mainLogoRes = R.drawable.main_logo,
                titleRes = R.string.playlist_detail_screen_delete_playlist_dialog_title,
                descriptionRes = R.string.playlist_detail_screen_delete_playlist_dialog_description,
                successRes = R.string.playlist_detail_screen_delete_playlist_dialog_accept_button_text,
                cancelRes = R.string.playlist_detail_screen_delete_playlist_dialog_cancel_button_text,
                onAcceptClicked = ::onDeletePlaylistConfirmed,
                onCancelClicked = ::onDeletePlaylistCancelled
            )
            FudgeTvScreenContent(
                error = errorMessage,
                onErrorAccepted = ::onErrorMessageCleared
            ) {
                if (isLoading) {
                    FudgeTvLoadingState(modifier = Modifier.fillMaxSize())
                } else if (channels.isEmpty()) {
                    FudgeTvNoContentState(
                        modifier = Modifier.fillMaxSize(),
                        messageRes = R.string.playlist_detail_screen_no_data_found_text
                    )
                } else {
                    PlaylistDetailMainContent(
                        channels = channels,
                        actionListener = actionListener
                    )
                }
            }
        }
    }
}

@Composable
private fun PlaylistDetailMainContent(
    channels: List<ChannelBO>,
    actionListener: PlaylistDetailScreenActionListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        PlaylistDetailHeader(actionListener = actionListener)
        PlaylistsChannelsGridContent(
            channels = channels,
            onChannelSelected = actionListener::onOpenChannelDetail
        )
    }
}

@Composable
private fun PlaylistDetailHeader(actionListener: PlaylistDetailScreenActionListener) {
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
            titleRes = R.string.playlist_detail_screen_title,
            textBold = true
        )
        FudgeTvButton(
            modifier = Modifier
                .width(150.dp)
                .fudgeTvPlaySoundEffectOnFocus(),
            type = FudgeTvButtonTypeEnum.SMALL,
            style = FudgeTvButtonStyleTypeEnum.TRANSPARENT,
            textRes = R.string.playlist_detail_screen_remove_playlist_button_text,
            onClick = actionListener::onDeletePlaylistClicked
        )
    }
}

@Composable
private fun PlaylistsChannelsGridContent(
    channels: List<ChannelBO>,
    onChannelSelected: (ChannelBO) -> Unit
) {
    FudgeTvFocusRequester { focusRequester ->
        FudgeTvLazyVerticalGrid(
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
private fun PlaylistChannelDetailsPopup(
    channel: ChannelBO,
    onPlayChannel: (ChannelBO) -> Unit,
    onRemoveFromPlaylist: (ChannelBO) -> Unit,
    onBackPressed: () -> Unit
) {
    with(channel) {
        FudgeTvPopupDialog(
            defaultImageRes = R.drawable.main_logo,
            imageUrl = icon,
            title = title,
            description = category,
            onBackPressed = onBackPressed
        ) { focusRequester ->
            Spacer(modifier = Modifier.weight(1f))
            FudgeTvButton(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fudgeTvPlaySoundEffectOnFocus()
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                type = FudgeTvButtonTypeEnum.MEDIUM,
                style = FudgeTvButtonStyleTypeEnum.NORMAL,
                textRes = R.string.playlist_detail_screen_channel_detail_popup_open_player_button_text,
                onClick = { onPlayChannel(channel) }
            )
            FudgeTvButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .fudgeTvPlaySoundEffectOnFocus()
                    .padding(bottom = 12.dp),
                type = FudgeTvButtonTypeEnum.MEDIUM,
                style = FudgeTvButtonStyleTypeEnum.TRANSPARENT,
                textRes = R.string.playlist_detail_screen_detail_popup_remove_from_playlist_button_text,
                onClick = { onRemoveFromPlaylist(channel) }
            )
        }
    }
}