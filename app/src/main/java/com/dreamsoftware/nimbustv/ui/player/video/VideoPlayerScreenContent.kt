package com.dreamsoftware.nimbustv.ui.player.video

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.dreamsoftware.nimbustv.ui.core.components.CommonChannelHeaderInfo
import com.dreamsoftware.nimbustv.ui.core.components.CommonFavoriteButton
import com.dreamsoftware.nimbustv.ui.core.components.CommonVideoBackground
import com.dreamsoftware.nimbustv.ui.core.player.PlayerControlsState
import com.dreamsoftware.nimbustv.ui.core.player.rememberVideoPlayerState
import com.dreamsoftware.nimbustv.ui.core.player.state.PlayerState
import com.dreamsoftware.nimbustv.ui.core.player.state.PlayerStateListener
import kotlinx.coroutines.launch

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun VideoPlayerScreenContent(
    state: VideoPlayerUiState
) {

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
    CommonVideoBackground(
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
            onFavoriteStateChanged = { },
            onOpenSettingsPressed = { }
        )
    }
}

@Composable
private fun PlayerControls(
    modifier: Modifier = Modifier,
    uiState: VideoPlayerUiState,
    isPlaying: Boolean,
    onFavoriteStateChanged: (Boolean) -> Unit,
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
                    title = title,
                    subtitle = subtitle,
                    logo = channelLogo
                )
                Spacer(modifier = Modifier.weight(1.0f))
                Row(
                    modifier = Modifier.padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    FudgeTvFocusRequester { focusRequester ->
                        CommonFavoriteButton(
                            modifier = Modifier.focusRequester(focusRequester),
                            isFavorite = false,
                            onStateChanged = onFavoriteStateChanged
                        )
                    }
                }
            }
        }
    }
}