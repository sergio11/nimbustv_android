package com.dreamsoftware.nimbustv.ui.core.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dreamsoftware.fudge.component.FudgeTvLoadingState
import com.dreamsoftware.fudge.component.FudgeTvScreenContent
import com.dreamsoftware.nimbustv.domain.model.PlayListBO

@Composable
fun CommonPlaylistScreenContent(
    isLoading: Boolean = false,
    isImportPlaylistDialogVisible: Boolean = false,
    playlists: List<PlayListBO> = emptyList(),
    playlistAlias: String,
    playListUrl: String,
    isImporting: Boolean = false,
    errorMessage: String?,
    onImportNewPlaylistClicked: () -> Unit,
    onErrorMessageCleared: () -> Unit,
    onImportNewPlayListConfirmed: () -> Unit,
    onNewPlayListUrlUpdated: (String) -> Unit,
    onNewPlayListAliasUpdated: (String) -> Unit,
    onImportNewPlaylistCancelled: () -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    FudgeTvScreenContent(
        error = errorMessage,
        onErrorAccepted = onErrorMessageCleared
    ) {
        ImportPlaylistDialog(
            isVisible = isImportPlaylistDialogVisible,
            isImporting = isImporting,
            playListUrl = playListUrl,
            playlistAlias = playlistAlias,
            onAcceptClicked = onImportNewPlayListConfirmed,
            onPlayListAliasUpdated = onNewPlayListAliasUpdated,
            onPlayListUrlUpdated = onNewPlayListUrlUpdated,
            onCancelClicked = onImportNewPlaylistCancelled
        )
        if (isLoading) {
            FudgeTvLoadingState(modifier = Modifier.fillMaxSize())
        } else if (playlists.isEmpty()) {
            NoPlaylistFound(onImportClicked = onImportNewPlaylistClicked)
        } else {
            content()
        }
    }
}