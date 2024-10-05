package com.dreamsoftware.nimbustv.ui.screens.home

import androidx.compose.runtime.Composable
import com.dreamsoftware.fudge.component.FudgeTvScreenContent
import com.dreamsoftware.nimbustv.ui.screens.home.components.importer.ImportPlaylistDialog
import com.dreamsoftware.nimbustv.ui.screens.home.components.importer.NoPlaylistFound

@Composable
internal fun HomeScreenContent(
    state: HomeUiState,
    actionListener: HomeScreenActionListener
) {
    with(state) {
        with(actionListener) {
            FudgeTvScreenContent(onErrorAccepted = ::onErrorMessageCleared) {
                ImportPlaylistDialog(
                    isVisible = isImportPlaylistDialogVisible,
                    playListUrl = newPlayListUrl,
                    onAcceptClicked = {},
                    onPlayListUrlUpdated = ::onNewPlayListUrlUpdated,
                    onCancelClicked = ::onImportNewPlaylistCancelled
                )
                NoPlaylistFound(onImportClicked = ::onImportNewPlaylistClicked)
            }
        }
    }
}