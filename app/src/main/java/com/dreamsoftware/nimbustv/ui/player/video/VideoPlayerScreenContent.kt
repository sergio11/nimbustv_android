package com.dreamsoftware.nimbustv.ui.player.video

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dreamsoftware.fudge.component.FudgeTvFocusRequester
import com.dreamsoftware.fudge.component.FudgeTvScreenContent
import com.dreamsoftware.fudge.component.player.video.FudgeTvVideoPlayerControlsIcon
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.ui.core.components.CommonChannelHeaderInfo
import com.dreamsoftware.nimbustv.ui.core.components.CommonPlayerBackground
import com.dreamsoftware.nimbustv.ui.core.components.FavouriteButton
import com.dreamsoftware.nimbustv.ui.core.player.PlayerControlsState
import com.dreamsoftware.nimbustv.ui.core.player.rememberVideoPlayerState
import com.dreamsoftware.nimbustv.ui.core.player.state.PlayerState
import com.dreamsoftware.nimbustv.ui.core.player.state.PlayerStateListener
import com.dreamsoftware.nimbustv.ui.player.video.components.VideoPlayerControlsIcon
import kotlinx.coroutines.launch

@SuppressLint("UnsafeOptInUsageError")
@Composable
internal fun VideoPlayerScreenContent(
    state: VideoPlayerUiState,
    actionListener: VideoPlayerScreenActionListener
) {
    with(actionListener) {
        val coroutineScope = rememberCoroutineScope()
        var playerState: PlayerState by remember { mutableStateOf(PlayerState.Idle) }
        val videoPlayerState = rememberVideoPlayerState(hideSeconds = 4, coroutineScope)
        val stateListener = remember {
            object : PlayerStateListener {
                override fun on(state: PlayerState) {
                    Log.d("PlayerScreenContent", "State $state")
                    playerState = state
                }
            }
        }
        FudgeTvScreenContent(onErrorAccepted = ::onErrorMessageCleared) {
            CommonPlayerBackground(
                videResource = state.videoUrl,
                videoResourceLicenseKey = state.licenseKey,
                playerStateListener = stateListener,
                onEnter = {
                    if (!videoPlayerState.isDisplayed) {
                        coroutineScope.launch {
                            videoPlayerState.showControls()
                        }
                    }
                }
            ) {
                PlayerControls(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    uiState = state,
                    isPlaying = playerState is PlayerState.Playing,
                    state = videoPlayerState,
                    onFavoriteClicked = { isFavorite ->
                        if(isFavorite) {
                            onAddFavoriteChannelClicked()
                        } else {
                            onRemoveChannelFromFavorites()
                        }
                    },
                    onOpenSettingsPressed = ::onOpenSettingsClicked
                )
            }
        }
    }
}

@Composable
private fun PlayerControls(
    modifier: Modifier = Modifier,
    uiState: VideoPlayerUiState,
    isPlaying: Boolean,
    onFavoriteClicked: (Boolean) -> Unit,
    onOpenSettingsPressed: () -> Unit,
    state: PlayerControlsState
) {
    LaunchedEffect(isPlaying) {
        if (!isPlaying) {
            state.showControls(seconds = Int.MAX_VALUE)
        } else {
            state.showControls()
        }
    }
    with(uiState) {
        AnimatedVisibility(
            modifier = modifier,
            visible = state.isDisplayed,
            enter = expandVertically { it },
            exit = shrinkVertically { it },
        ) {
            Column(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black,
                                Color.Transparent,
                                Color.Black,
                            ),
                        ),
                    )
                    .padding(
                        horizontal = 56.dp,
                        vertical = 32.dp,
                    ),
            ) {
                CommonChannelHeaderInfo(
                    modifier = Modifier.fillMaxWidth(),
                    title = title,
                    subtitle = subtitle,
                    logo = channelLogo
                )
                Spacer(modifier = Modifier.weight(1.0f))
                FudgeTvFocusRequester { focusRequester ->
                    Row(
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        FavouriteButton(
                            modifier = Modifier.focusRequester(focusRequester),
                            isFavorite = isFavoriteChannel,
                            size = 50.dp,
                            onClick = { onFavoriteClicked(!isFavoriteChannel) }
                        )
                        VideoPlayerControlsIcon(
                            modifier = Modifier.padding(start = 12.dp),
                            icon = R.drawable.settings,
                            state = state,
                            size = 50.dp,
                            isPlaying = isPlaying,
                            contentDescription = "Settings Icon",
                            onClick = onOpenSettingsPressed
                        )
                    }
                }
            }
        }
    }
}