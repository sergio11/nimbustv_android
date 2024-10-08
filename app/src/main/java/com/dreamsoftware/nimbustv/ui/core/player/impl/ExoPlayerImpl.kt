package com.dreamsoftware.nimbustv.ui.core.player.impl

import android.view.View
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.hls.HlsMediaSource
import com.dreamsoftware.nimbustv.ui.core.player.SupportPlayer
import com.dreamsoftware.nimbustv.ui.core.player.state.PlayerStateListener

@UnstableApi internal class ExoPlayerImpl(
    private val player: ExoPlayer,
    private val providePlayerView: () -> View
) : SupportPlayer {

    private companion object {
        private const val RAW_RESOURCE_SCHEME = "rawresource"
        private const val HLS_EXTENSION = ".m3u8"
        private const val DASH_EXTENSION = ".mpd"
    }

    private var listener: Player.Listener? = null

    override fun play() {
        player.play()
    }

    override fun pause() {
        player.pause()
    }

    override fun stop() {
        player.stop()
    }

    override fun seekTo(positionMs: Long) {
        player.seekTo(positionMs)
    }

    override fun seekForward() {
        player.seekForward()
    }

    override fun seekBack() {
        player.seekBack()
    }

    /**
     * Prepares the media player with the appropriate media source based on the video resource's extension.
     *
     * @param videoResource The URI of the video resource to be played. The method checks the extension
     *                      to determine whether the resource is HLS or DASH format.
     */
    override fun prepare(videoResource: String) {
        val factory = DefaultHttpDataSource.Factory()
        when {
            videoResource.endsWith(HLS_EXTENSION) -> {
                HlsMediaSource.Factory(factory)
            }
            videoResource.endsWith(DASH_EXTENSION) -> {
                DashMediaSource.Factory(factory)
            }
            else -> null
        }?.let { mediaSource ->
            player.apply {
                setMediaSource(mediaSource.createMediaSource(MediaItem.fromUri(videoResource)))
                prepare()
            }
        }
    }

    override fun prepare(videoResourceId: Int) {
        player.apply {
            setMediaItem(MediaItem.fromUri("$RAW_RESOURCE_SCHEME:///$videoResourceId"))
            prepare()
        }
    }

    override fun release() {
        player.release()
    }

    override fun getView(): View  = providePlayerView()

    override val currentPosition: Long
        get() = player.currentPosition

    override val duration: Long
        get() = player.duration

    override val isPlaying: Boolean
        get() = player.isPlaying

    override fun setPlaybackEvent(callback: PlayerStateListener) {
        listener = ExoPlayerStateListener(callback, player).apply {
            player.addListener(this)
        }
    }

    override fun removePlaybackEvent(callback: PlayerStateListener) {
        listener?.let {
            player.removeListener(it)
        }
        listener = null
    }

}