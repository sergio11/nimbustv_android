package com.dreamsoftware.nimbustv.ui.core.player.state

import androidx.media3.common.PlaybackException

interface PlayerStateListener {
    fun onStateChanged(state: PlayerState) {}
    fun onErrorOccurred(cause: PlaybackException) {}
}