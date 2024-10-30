package com.dreamsoftware.nimbustv.ui.core.components

import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.compose.runtime.State
import androidx.media3.common.util.UnstableApi
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvLoadingState
import com.dreamsoftware.fudge.utils.handleDPadKeyEvents
import com.dreamsoftware.nimbustv.ui.core.player.PlayerFactory
import com.dreamsoftware.nimbustv.ui.core.player.SupportPlayer
import com.dreamsoftware.nimbustv.ui.core.player.state.PlayerStateListener
import com.dreamsoftware.nimbustv.utils.disableCertificateValidation

/**
 * Composable function for a common player background with player controls.
 *
 * @param modifier Modifier for the video background.
 * @param videResource video resource URL.
 * @param videoResourceId Raw resource ID for the video.
 * @param disableCertValidation Flag to disable certificate validation.
 * @param playerStateListener Listener for player state events.
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
    playerStateListener: PlayerStateListener? = null,
    onLeft: (() -> Unit)? = null,
    onRight: (() -> Unit)? = null,
    onEnter: (() -> Unit)? = null,
    content: @Composable BoxScope.(supportPlayer: SupportPlayer) -> Unit = {}
) {
    if (disableCertValidation) {
        disableCertificateValidation()
    }

    if(!videResource.isNullOrBlank() || videoResourceId != null) {
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

        if(!isOnlyAudio) {
            PlayerViewContent(
                modifier = modifier,
                player = player,
                onLeft = onLeft,
                onRight = onRight,
                onEnter = onEnter,
                content = content
            )
        } else {
            AudioPlayerViewContent(
                modifier = modifier,
                player = player,
                content = content
            )
        }
    } else {
        FudgeTvLoadingState(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize())
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
            player.release()
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
        AndroidView(factory = { player.getView() }, modifier = Modifier
            .fillMaxSize()
            .handleDPadKeyEvents(
                onEnter = onEnter,
                onLeft = onLeft,
                onRight = onRight
            )
            .focusable())
        content(player)
    }
}