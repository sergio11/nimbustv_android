package com.dreamsoftware.nimbustv.ui.core.player.impl

import android.util.Log
import android.view.View
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.drm.DefaultDrmSessionManager
import androidx.media3.exoplayer.drm.FrameworkMediaDrm
import androidx.media3.exoplayer.drm.HttpMediaDrmCallback
import androidx.media3.exoplayer.drm.LocalMediaDrmCallback
import androidx.media3.exoplayer.hls.HlsMediaSource
import com.dreamsoftware.nimbustv.ui.core.player.SupportPlayer
import com.dreamsoftware.nimbustv.ui.core.player.state.PlayerStateListener

@UnstableApi
internal class ExoPlayerImpl(
    private val player: ExoPlayer,
    private val providePlayerView: () -> View
) : SupportPlayer {

    private companion object {
        private const val TAG = "EXOPLAYER"
        private const val RAW_RESOURCE_SCHEME = "rawresource"
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

    override fun prepareHls(videoResource: String) {
        player.apply {
            setMediaSource(
                HlsMediaSource.Factory(DefaultHttpDataSource.Factory())
                    .createMediaSource(MediaItem.fromUri(videoResource))
            )
            prepare()
        }
    }

    override fun prepareDash(videoResource: String, licenseKey: String) {
        player.apply {
            // Check if `licenseKey` is a URL (starts with "http://" or "https://") or in the "kid:k" format.
            val drmCallback = if (licenseKey.startsWith("http://") || licenseKey.startsWith("https://")) {
                // Case 1: `licenseKey` is a full URL, use it directly as the DRM license server URL
                HttpMediaDrmCallback(licenseKey, DefaultHttpDataSource.Factory())
            } else {
                // Case 2: `licenseKey` is in "kid:k" format, generate DRM JSON manually
                val licenseParts = licenseKey.split(":")
                if (licenseParts.size == 2) {
                    val kid = licenseParts[0]  // First part as key ID (kid)
                    val k = licenseParts[1]    // Second part as the actual key (k)

                    // Log extracted keys for debugging
                    Log.d(TAG, "Extracted key ID (kid): $kid")
                    Log.d(TAG, "Extracted key (k): $k")

                    // Generate DRM JSON string using extracted `kid` and `k`
                    val keyString = """
                {
                    "keys": [
                        {
                            "kty": "oct",
                            "k": "$k",
                            "kid": "$kid"
                        }
                    ],
                    "type": "temporary"
                }
                """.trimIndent()

                    // Create the DRM callback with the generated JSON
                    LocalMediaDrmCallback(keyString.encodeToByteArray())
                } else {
                    // Handle error if `licenseKey` format is not valid
                    Log.d(TAG, "Invalid licenseKey format: $licenseKey")
                    return  // Exit if format is incorrect to avoid crashing
                }
            }

            try {
                // Use `videoResource` as the video resource URL
                setMediaSource(
                    DashMediaSource.Factory(DefaultHttpDataSource.Factory())
                        .setDrmSessionManagerProvider {
                            DefaultDrmSessionManager.Builder()
                                .setPlayClearSamplesWithoutKeys(true)
                                .setMultiSession(false)
                                .setKeyRequestParameters(emptyMap())
                                .setUuidAndExoMediaDrmProvider(
                                    C.CLEARKEY_UUID,
                                    FrameworkMediaDrm.DEFAULT_PROVIDER
                                )
                                .build(drmCallback)
                        }
                        .createMediaSource(MediaItem.fromUri(videoResource))  // Use videoResource as video URL
                )

                // Prepare the player
                prepare()
                Log.d(TAG, "Preparation complete for video resource: $videoResource")
            } catch (e: Exception) {
                Log.d(TAG, "Error during DRM setup: ${e.message}", e)
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

    override fun getView(): View = providePlayerView()

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