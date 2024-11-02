package com.dreamsoftware.nimbustv.ui.core.components

import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SentimentDissatisfied
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.PlaybackException
import androidx.media3.common.util.UnstableApi
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvLoadingState
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.fudge.utils.handleDPadKeyEvents
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.ui.core.player.PlayerFactory
import com.dreamsoftware.nimbustv.ui.core.player.SupportPlayer
import com.dreamsoftware.nimbustv.ui.core.player.state.PlayerState
import com.dreamsoftware.nimbustv.ui.core.player.state.PlayerStateListener
import com.dreamsoftware.nimbustv.utils.disableCertificateValidation

/**
 * Composable function for a common player background with player controls.
 *
 * @param modifier Modifier for the video background.
 * @param videResource video resource URL.
 * @param videoResourceId Raw resource ID for the video.
 * @param disableCertValidation Flag to disable certificate validation.
 * @param onStateChanged Callback for playback state changed events
 * @param onLeft Callback for left D-pad key press.
 * @param onRight Callback for right D-pad key press.
 * @param onEnter Callback for enter key press.
 * @param content Lambda to define additional content inside the BoxScope.
 */
@UnstableApi
@Composable
fun CommonPlayerBackground(
    modifier: Modifier = Modifier,
    videResource: String? = null,
    @RawRes videoResourceId: Int? = null,
    videoResourceLicenseKey: String? = null,
    disableCertValidation: Boolean = true,
    isOnlyAudio: Boolean = false,
    onStateChanged: ((PlayerState) -> Unit)? = null,
    onLeft: (() -> Unit)? = null,
    onRight: (() -> Unit)? = null,
    onEnter: (() -> Unit)? = null,
    content: @Composable BoxScope.(supportPlayer: SupportPlayer) -> Unit = {}
) {
    var playbackError by remember { mutableStateOf(false) }
    var isPlaybackLoading by remember { mutableStateOf(true) }
    val playerStateListener = remember {
        object : PlayerStateListener {
            override fun onStateChanged(state: PlayerState) {
                onStateChanged?.invoke(state)
                if (state is PlayerState.Buffering || state is PlayerState.Complete) {
                    playbackError = false
                    isPlaybackLoading = true
                }
                if (state is PlayerState.Playing) {
                    playbackError = false
                    isPlaybackLoading = false
                }
            }

            override fun onErrorOccurred(cause: PlaybackException) {
                isPlaybackLoading = false
                playbackError = true
            }
        }
    }

    if (disableCertValidation) {
        disableCertificateValidation()
    }

    if (!videResource.isNullOrBlank() || videoResourceId != null) {
        val context = LocalContext.current
        val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
        val player = remember { PlayerFactory.create(context) }

        InitializeAndPlayVideo(
            lifecycleOwner = lifecycleOwner,
            player = player,
            videResource = videResource,
            videoResourceId = videoResourceId,
            videoResourceLicenseKey = videoResourceLicenseKey,
            playerStateListener = playerStateListener
        )

        if (isOnlyAudio) {
            AudioPlayerViewContent(
                modifier = modifier,
                player = player,
                content = content
            )
        } else {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                PlayerViewContent(
                    modifier = modifier,
                    player = player,
                    onLeft = onLeft,
                    onRight = onRight,
                    onEnter = onEnter,
                    content = content
                )
                if (isPlaybackLoading) {
                    PlaybackLoading()
                }
                if (playbackError) {
                    PlaybackError()
                }
            }
        }
    } else {
        PlaybackLoading()
    }
}

/**
 * Helper function to initialize and play the video based on lifecycle events.
 *
 * @param lifecycleOwner State holding the current LifecycleOwner.
 * @param player SupportPlayer instance for video playback.
 * @param videResource video resource URL.
 * @param videoResourceId Raw resource ID for the video.
 * @param playerStateListener Listener for player state events.
 */
@Composable
private fun InitializeAndPlayVideo(
    lifecycleOwner: State<LifecycleOwner>,
    player: SupportPlayer,
    videResource: String? = null,
    videoResourceLicenseKey: String? = null,
    @RawRes videoResourceId: Int? = null,
    playerStateListener: PlayerStateListener? = null
) {
    val playContent = {
        with(player) {
            videResource?.let {
                videoResourceLicenseKey?.takeIf { it.isNotBlank() }?.let { licenseKey ->
                    prepareDash(videResource, licenseKey)
                } ?: prepareHls(videResource)
            } ?: run {
                videoResourceId?.let {
                    prepare(it)
                }
            }
            play()
        }
    }

    playerStateListener?.let { stateListener ->
        LaunchedEffect(Unit) {
            player.setPlaybackEvent(callback = stateListener)
        }
    }

    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    player.pause()
                }

                Lifecycle.Event.ON_RESUME -> {
                    playContent()
                }

                else -> {}
            }
        }
        val lifecycle = lifecycleOwner.value.lifecycle
        lifecycle.addObserver(observer)
        onDispose {
            player.apply {
                playerStateListener?.let(::removePlaybackEvent)
                release()
            }
            lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(videResource ?: videoResourceId) {
        val lifecycle = lifecycleOwner.value.lifecycle
        if (lifecycle.currentState == Lifecycle.State.RESUMED) {
            playContent()
        }
    }
}

@Composable
private fun AudioPlayerViewContent(
    modifier: Modifier = Modifier,
    player: SupportPlayer,
    content: @Composable BoxScope.(supportPlayer: SupportPlayer) -> Unit = {}
) {
    Box(modifier = modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
        content(player)
    }
}


/**
 * Composable function to display the player view content within a Box.
 *
 * @param modifier Modifier for the player view content.
 * @param player SupportPlayer instance for video playback.
 * @param onLeft Callback for left D-pad key press.
 * @param onRight Callback for right D-pad key press.
 * @param onEnter Callback for enter key press.
 * @param content Lambda to define additional content inside the BoxScope.
 */
@Composable
private fun PlayerViewContent(
    modifier: Modifier = Modifier,
    player: SupportPlayer,
    onLeft: (() -> Unit)? = null,
    onRight: (() -> Unit)? = null,
    onEnter: (() -> Unit)? = null,
    content: @Composable BoxScope.(supportPlayer: SupportPlayer) -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AndroidView(
            factory = { player.getView() }, modifier = Modifier
                .fillMaxSize()
                .handleDPadKeyEvents(
                    onEnter = onEnter,
                    onLeft = onLeft,
                    onRight = onRight
                )
                .focusable()
        )
        content(player)
    }
}

@Composable
private fun PlaybackLoading() {
    FudgeTvLoadingState(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    )
}

@Composable
private fun PlaybackError() {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Rounded.SentimentDissatisfied,
            modifier = Modifier.size(64.dp),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(30.dp))
        FudgeTvText(
            titleRes = R.string.channel_playback_error_occurred,
            type = FudgeTvTextTypeEnum.LABEL_LARGE,
            textAlign = TextAlign.Center
        )
    }
}
