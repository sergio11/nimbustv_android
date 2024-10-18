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
import androidx.compose.ui.unit.dp
import com.dreamsoftware.fudge.component.FudgeTvButton
import com.dreamsoftware.fudge.component.FudgeTvButtonStyleTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvButtonTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvFocusRequester
import com.dreamsoftware.fudge.component.FudgeTvLoadingState
import com.dreamsoftware.fudge.component.FudgeTvNoContentState
import com.dreamsoftware.fudge.component.FudgeTvScreenContent
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.model.PlayListBO
import com.dreamsoftware.nimbustv.ui.core.components.CommonLazyVerticalGrid
import com.dreamsoftware.nimbustv.ui.core.components.ImportPlaylistDialog
import com.dreamsoftware.nimbustv.ui.core.components.PlaylistItem
import com.dreamsoftware.nimbustv.ui.screens.onboarding.playSoundEffectOnFocus

@Composable
internal fun PlaylistsScreenContent(
    uiState: PlaylistsUiState,
    actionListener: PlaylistsScreenActionListener
) {
    with(uiState) {
        with(actionListener) {
            ImportPlaylistDialog(
                isVisible = isImportPlaylistDialogVisible,
                isImporting = isLoading,
                playListUrl = newPlayListUrl,
                playlistAlias = newPlayListAlias,
                onAcceptClicked = ::onImportNewPlayListConfirmed,
                onPlayListAliasUpdated = ::onNewPlayListAliasUpdated,
                onPlayListUrlUpdated = ::onNewPlayListUrlUpdated,
                onCancelClicked = ::onImportNewPlaylistCancelled
            )
            FudgeTvScreenContent(onErrorAccepted = actionListener::onErrorMessageCleared) {
                if (isLoading) {
                    FudgeTvLoadingState(modifier = Modifier.fillMaxSize())
                } else if (playlists.isEmpty()) {
                    FudgeTvNoContentState(
                        modifier = Modifier.fillMaxSize(),
                        messageRes = R.string.my_playlists_screen_no_data_found_text
                    )
                } else {
                    PlaylistMainContent(
                        playlists = playlists,
                        actionListener = actionListener
                    )
                }
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
                .playSoundEffectOnFocus(),
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
        CommonLazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            state = rememberLazyGridState(),
            items = playlists
        ) { item ->
            PlaylistItem(
                playlist = item,
                onPlaylistSelected = onPlayListSelected
            )
        }
    }
}