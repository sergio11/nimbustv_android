package com.dreamsoftware.nimbustv.ui.core.player.state

sealed class PlayerState {
    data object Playing : PlayerState()
    data object Pause : PlayerState()
    data object Stop : PlayerState()
    data object Idle : PlayerState()
    data object Buffering : PlayerState()
    data object Complete : PlayerState()

    class SeekStart(position: Long) : PlayerState()
    class SeekEnd(position: Long) : PlayerState()
}