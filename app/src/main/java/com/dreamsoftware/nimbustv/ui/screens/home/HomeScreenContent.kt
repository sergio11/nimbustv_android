package com.dreamsoftware.nimbustv.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvButton
import com.dreamsoftware.fudge.component.FudgeTvButtonStyleTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvButtonTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvFocusRequester
import com.dreamsoftware.fudge.component.FudgeTvLoadingState
import com.dreamsoftware.fudge.component.FudgeTvScreenContent
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.fudge.utils.conditional
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.model.PlayListBO
import com.dreamsoftware.nimbustv.ui.core.components.ChannelGridItem
import com.dreamsoftware.nimbustv.ui.core.components.ChannelPreview
import com.dreamsoftware.nimbustv.ui.core.components.CommonChip
import com.dreamsoftware.nimbustv.ui.core.components.CommonLazyVerticalGrid
import com.dreamsoftware.nimbustv.ui.core.components.PlaylistItem
import com.dreamsoftware.nimbustv.ui.core.components.ImportPlaylistDialog
import com.dreamsoftware.nimbustv.ui.screens.home.components.importer.NoPlaylistFound
import com.dreamsoftware.nimbustv.ui.screens.onboarding.playSoundEffectOnFocus

@Composable
internal fun HomeScreenContent(
    state: HomeUiState,
    actionListener: HomeScreenActionListener
) {
    with(state) {
        with(actionListener) {
            with(MaterialTheme.colorScheme) {
                FudgeTvScreenContent(onErrorAccepted = ::onErrorMessageCleared) {
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
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (playlists.isEmpty()) {
                            NoPlaylistFound(onImportClicked = ::onImportNewPlaylistClicked)
                        } else {
                            PlayListsColumn(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.2f)
                                    .background(primaryContainer.copy(alpha = 0.5f))
                                    .border(1.dp, primary),
                                playlists = playlists,
                                playlistSelected = playlistSelected,
                                onManagePlaylistClicked = actionListener::onManagePlaylistClicked,
                                onPlaylistSelected = actionListener::onNewPlaylistSelected
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (isLoading) {
                                    FudgeTvLoadingState(modifier = Modifier.fillMaxSize())
                                } else {
                                    channelFocused?.let { channel ->
                                        ChannelPreview(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .fillMaxHeight(0.45f)
                                                .padding(start = 8.dp),
                                            channel = channel
                                        )
                                    }
                                    CategoriesList(
                                        categories = categories,
                                        categorySelected = categorySelected,
                                        onCategorySelected = actionListener::onNewCategorySelected
                                    )
                                    ChannelsGrid(
                                        channels = channels,
                                        channelFocused = channelFocused,
                                        onChannelFocused = actionListener::onChannelFocused,
                                        onChannelPressed = actionListener::onChannelPressed
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
private fun CategoriesList(
    modifier: Modifier = Modifier,
    categories: List<String>,
    categorySelected: String?,
    onCategorySelected: (String) -> Unit
) {
    val listState = rememberLazyListState()
    LaunchedEffect(categorySelected) {
        if (categorySelected != null) {
            categories.indexOf(categorySelected).takeIf { it >= 0 }?.let {
                listState.scrollToItem(it)
            }
        }
    }
    LazyRow(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(categories.size) { idx ->
            val category = categories[idx]
            CommonChip(
                isSelected = category == categorySelected,
                text = category,
                onSelected = {
                    onCategorySelected(category)
                }
            )
        }
    }
}

@Composable
private fun ChannelsGrid(
    modifier: Modifier = Modifier,
    channelFocused: ChannelBO? = null,
    channels: List<ChannelBO>,
    onChannelFocused: (ChannelBO) -> Unit,
    onChannelPressed: (ChannelBO) -> Unit,
) {
    val listState = rememberLazyListState()
    LaunchedEffect(channelFocused) {
        if (channelFocused != null) {
            channels.indexOf(channelFocused).takeIf { it >= 0 }?.let {
                listState.scrollToItem(it)
            }
        }
    }
    FudgeTvFocusRequester(shouldRequestFocus = {
        channels.isNotEmpty() && channelFocused != null
    }) { requester ->
        CommonLazyVerticalGrid(
            modifier = modifier.fillMaxWidth(),
            state = rememberLazyGridState(),
            items = channels
        ) { item ->
            ChannelGridItem(
                modifier = if (item == channelFocused) Modifier.focusRequester(requester) else Modifier,
                channel = item,
                onChannelFocused = onChannelFocused,
                onChannelPressed = onChannelPressed
            )
        }
    }
}

@Composable
private fun PlayListsColumn(
    modifier: Modifier,
    playlists: List<PlayListBO>,
    playlistSelected: PlayListBO? = null,
    onManagePlaylistClicked: () -> Unit,
    onPlaylistSelected: (PlayListBO) -> Unit
) {
    FudgeTvFocusRequester(shouldRequestFocus = {
        playlists.isNotEmpty() && playlistSelected != null
    }) { requester ->
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FudgeTvText(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 4.dp),
                titleRes = R.string.home_screen_playlist_column_title_text,
                type = FudgeTvTextTypeEnum.TITLE_MEDIUM,
                textColor = MaterialTheme.colorScheme.onSurface,
                textBold = true,
                textAlign = TextAlign.Center
            )
            FudgeTvButton(
                modifier = Modifier
                    .width(150.dp)
                    .playSoundEffectOnFocus(),
                type = FudgeTvButtonTypeEnum.SMALL,
                style = FudgeTvButtonStyleTypeEnum.TRANSPARENT,
                textRes = R.string.home_screen_manage_playlist_button_text,
                onClick = onManagePlaylistClicked
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                modifier = Modifier.weight(1f, true),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(8.dp)
            ) {
                items(playlists.size) { idx ->
                    val playlist = playlists[idx]
                    val isSelected = playlist == playlistSelected
                    PlaylistItem(
                        modifier = Modifier.conditional(condition = isSelected, ifTrue = {
                            focusRequester(requester)
                        }),
                        isSelected = isSelected,
                        playlist = playlist,
                        onPlaylistSelected = onPlaylistSelected
                    )
                }
            }
        }
    }
}