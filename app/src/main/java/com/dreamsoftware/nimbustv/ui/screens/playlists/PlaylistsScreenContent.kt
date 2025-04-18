package com.dreamsoftware.nimbustv.ui.screens.playlists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.dreamsoftware.fudge.component.FudgeTvFocusRequester
import com.dreamsoftware.fudge.component.FudgeTvLazyVerticalGrid
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.fudge.component.fudgeTvPlaySoundEffectOnFocus
import com.dreamsoftware.fudge.utils.conditional
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.model.PlayListBO
import com.dreamsoftware.nimbustv.ui.core.components.CommonPlaylistScreenContent
import com.dreamsoftware.nimbustv.ui.core.components.CommonSelectableItem

@Composable
internal fun PlaylistsScreenContent(
    uiState: PlaylistsUiState,
    actionListener: PlaylistsScreenActionListener
) {
    with(uiState) {
        with(actionListener) {
            CommonPlaylistScreenContent(
                isLoading = isLoading,
                isImporting = isImporting,
                isImportPlaylistDialogVisible = isImportPlaylistDialogVisible,
                errorMessage = errorMessage,
                playlists = playlists,
                playListUrl = newPlayListUrl,
                playlistAlias = newPlayListAlias,
                onErrorMessageCleared = ::onErrorMessageCleared,
                onImportNewPlaylistClicked = ::onImportNewPlaylistClicked,
                onImportNewPlayListConfirmed = ::onImportNewPlayListConfirmed,
                onNewPlayListAliasUpdated = ::onNewPlayListAliasUpdated,
                onNewPlayListUrlUpdated = ::onNewPlayListUrlUpdated,
                onImportNewPlaylistCancelled = ::onImportNewPlaylistCancelled
            ) {
                PlaylistMainContent(
                    playlists = playlists,
                    actionListener = actionListener
                )
            }
        }
    }
}

@Composable
private fun PlaylistMainContent(
    playlists: List<PlayListBO>,
    actionListener: PlaylistsScreenActionListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        PlaylistHeader(actionListener = actionListener)
        PlaylistsGridContent(
            playlists = playlists,
            onPlayListSelected = actionListener::onOpenPlaylistDetailClicked
        )
    }
}

@Composable
private fun PlaylistHeader(actionListener: PlaylistsScreenActionListener) {
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
            titleRes = R.string.my_playlists_screen_title,
            textBold = true
        )
        FudgeTvButton(
            modifier = Modifier
                .width(150.dp)
                .fudgeTvPlaySoundEffectOnFocus(),
            type = FudgeTvButtonTypeEnum.SMALL,
            style = FudgeTvButtonStyleTypeEnum.TRANSPARENT,
            textRes = R.string.my_playlists_screen_import_new_playlist_button_text,
            onClick = actionListener::onImportNewPlaylistClicked
        )
    }
}

@Composable
private fun PlaylistsGridContent(
    playlists: List<PlayListBO>,
    onPlayListSelected: (PlayListBO) -> Unit
) {
    FudgeTvFocusRequester { focusRequester ->
        FudgeTvLazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            state = rememberLazyGridState(),
            items = playlists
        ) { idx, item ->
            CommonSelectableItem(
                modifier = Modifier.conditional(idx == 0, ifTrue = {
                    focusRequester(focusRequester)
                }),
                titleText = item.alias,
                subtitleText = "Channels ( ${item.channelsCount} )",
                onItemSelected = { onPlayListSelected(item) }
            )
        }
    }
}