package com.dreamsoftware.nimbustv.ui.screens.player.audio

import android.util.Log
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvFocusRequester
import com.dreamsoftware.fudge.component.player.FudgeTvPlayerTitle
import com.dreamsoftware.fudge.component.player.audio.FudgeTvAudioPlayerControlsIcon
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.ui.core.components.ChannelLogo
import com.dreamsoftware.nimbustv.ui.core.components.CommonPlayerBackground
import com.dreamsoftware.nimbustv.ui.core.player.SupportPlayer
import com.dreamsoftware.nimbustv.ui.core.player.state.PlayerState
import com.dreamsoftware.nimbustv.ui.core.player.state.PlayerStateListener
import com.dreamsoftware.nimbustv.ui.utils.EMPTY

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
internal fun AudioPlayerScreenContent(
    state: AudioPlayerUiState,
) {
    var playerState: PlayerState by remember { mutableStateOf(PlayerState.Idle) }
    val infiniteTransition = rememberInfiniteTransition(label = String.EMPTY)
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500),
            repeatMode = RepeatMode.Reverse
        ),
        label = String.EMPTY
    )
    val stateListener = remember {
        object : PlayerStateListener {
            override fun on(state: PlayerState) {
                Log.d("AudioScreenContent", "State $state")
                playerState = state
            }
        }
    }
    with(state) {
        Column(
            modifier = Modifier
                .focusable()
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primaryContainer),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ChannelLogo(
                modifier = Modifier
                    .scale(scale)
                    .clip(RoundedCornerShape(16.dp)),
                size = 196.dp,
                logo = state.imageUrl
            )
            FudgeTvPlayerTitle(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                descriptionModifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                title = title,
                description = "$title • $category"
            )
            CommonPlayerBackground(
                modifier = Modifier.padding(top = 50.dp),
                videResource = state.audioUrl,
                videoResourceLicenseKey = state.licenseKey,
                playerStateListener = stateListener,
                isOnlyAudio = true
            ) { supportPlayer ->
                AudioPlayerControls(
                    player = supportPlayer,
                    isPlaying = playerState is PlayerState.Playing,
                )
            }
        }
    }
}

@Composable
private fun AudioPlayerControls(
    player: SupportPlayer,
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    with(player) {
        FudgeTvFocusRequester { focusRequester ->
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FudgeTvAudioPlayerControlsIcon(
                    modifier = Modifier.focusRequester(focusRequester),
                    icon = if (!isPlaying) R.drawable.play_icon else R.drawable.pause,
                    buttonColor = MaterialTheme.colorScheme.onBackground,
                    size = 56.dp
                ) {
                    if (isPlaying) {
                        pause()
                    } else {
                        play()
                    }
                }
            }
        }
    }
}