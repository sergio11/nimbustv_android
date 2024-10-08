package com.dreamsoftware.nimbustv.ui.core.player

import android.view.View
import androidx.annotation.RawRes
import com.dreamsoftware.nimbustv.ui.core.player.state.PlayerStateListener

/**
 * Interface defining a media player with common playback controls and event handling.
 */
interface SupportPlayer {

    /**
     * Starts playback of the media.
     */
    fun play()

    /**
     * Pauses the currently playing media.
     */
    fun pause()

    /**
     * Stops playback of the media.
     */
    fun stop()

    /**
     * Seeks to the specified position in the media.
     *
     * @param positionMs The position to seek to, in milliseconds.
     */
    fun seekTo(positionMs: Long)

    /**
     * Seeks forward in the media.
     */
    fun seekForward()

    /**
     * Seeks backward in the media.
     */
    fun seekBack()

    /**
     * Prepares the media player with the appropriate media source based on the video resource's extension.
     *
     * @param videoResource The URI of the video resource to be played. The method checks the extension
     *                      to determine whether the resource is HLS or DASH format.
     */
    fun prepare(videoResource: String)

    /**
     * Prepares the player for playback using a raw resource ID.
     *
     * @param videoResourceId The raw resource ID to prepare.
     */
    fun prepare(@RawRes videoResourceId: Int)

    /**
     * Releases the resources used by the player.
     */
    fun release()

    /**
     * Retrieves the view associated with the player.
     *
     * @return The player's view.
     */
    fun getView(): View

    /**
     * Retrieves the current playback position in the media.
     */
    val currentPosition: Long

    /**
     * Retrieves the duration of the media.
     */
    val duration: Long

    /**
     * Checks if the media is currently playing.
     */
    val isPlaying: Boolean

    /**
     * Sets a callback to receive playback events.
     *
     * @param callback The callback to be notified of playback events.
     */
    fun setPlaybackEvent(callback: PlayerStateListener)

    /**
     * Removes a playback event callback.
     *
     * @param callback The callback to be removed.
     */
    fun removePlaybackEvent(callback: PlayerStateListener)
}