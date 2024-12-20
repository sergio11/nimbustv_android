package com.dreamsoftware.nimbustv.ui.core.player.impl

import android.util.Log
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_BUFFERING
import androidx.media3.common.Player.STATE_ENDED
import androidx.media3.common.Player.STATE_READY
import androidx.media3.exoplayer.ExoPlayer
import com.dreamsoftware.nimbustv.ui.core.player.state.PlayerState
import com.dreamsoftware.nimbustv.ui.core.player.state.PlayerStateListener

internal class ExoPlayerStateListener(
    private val stateListener: PlayerStateListener,
    val player: ExoPlayer
) : Player.Listener {

    private companion object {
        const val TAG = "EXOPLAYER_LISTENER"
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        Log.d(TAG, "onIsPlayingChanged $isPlaying")
        stateListener.onStateChanged(getStateWhen(isPlaying))
    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        Log.d(TAG, "onPlayerError $error")
        stateListener.onErrorOccurred(error)
    }

    override fun onPlayerErrorChanged(error: PlaybackException?) {
        super.onPlayerErrorChanged(error)
        Log.d(TAG, "onPlayerErrorChanged $error")
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        Log.d(TAG, "onPlaybackStateChanged $playbackState")
        val state = when (playbackState) {
            STATE_BUFFERING -> PlayerState.Buffering
            STATE_READY -> {
                val isPlaying = player.playWhenReady
                getStateWhen(isPlaying)
            }

            STATE_ENDED -> PlayerState.Complete
            else -> PlayerState.Idle
        }
        stateListener.onStateChanged(state)
    }

    private fun getStateWhen(playing: Boolean) = if (playing)
        PlayerState.Playing
    else
        PlayerState.Pause
}