package com.dreamsoftware.nimbustv.ui.screens.playlists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dreamsoftware.fudge.component.FudgeTvFocusRequester
import com.dreamsoftware.fudge.component.FudgeTvLoadingState
import com.dreamsoftware.fudge.component.FudgeTvNoContentState
import com.dreamsoftware.fudge.component.FudgeTvScreenContent
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.ui.core.components.CommonLazyVerticalGrid
import com.dreamsoftware.nimbustv.ui.core.components.PlaylistItem

@Composable
internal fun PlaylistsScreenContent(
    uiState: PlaylistsUiState,
    actionListener: PlaylistsScreenActionListener
) {
    with(uiState) {
        FudgeTvScreenContent(onErrorAccepted = actionListener::onErrorMessageCleared) {
            if (isLoading) {
                FudgeTvLoadingState(modifier = Modifier.fillMaxSize())
            } else if(playlists.isEmpty()) {
                FudgeTvNoContentState(
                    modifier = Modifier.fillMaxSize(),
                    messageRes = R.string.my_playlists_screen_no_data_found_text
                )
            }else {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    FudgeTvText(
                        modifier = Modifier.padding(bottom = 8.dp, top = 56.dp, start = 32.dp, end = 32.dp),
                        type = FudgeTvTextTypeEnum.HEADLINE_MEDIUM,
                        titleRes = R.string.my_playlists_screen_title,
                        textBold = true
                    )
                    FudgeTvFocusRequester { focusRequester ->
                        CommonLazyVerticalGrid(
                            modifier = Modifier.fillMaxWidth(),
                            state = rememberLazyGridState(),
                            items = playlists
                        ) { item ->
                            PlaylistItem(
                                isSelected = false,
                                playlist = item,
                                onPlaylistSelected = { }
                            )
                        }
                    }
                }
            }
        }
    }
}