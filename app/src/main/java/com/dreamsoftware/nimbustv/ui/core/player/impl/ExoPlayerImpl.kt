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
import java.nio.ByteBuffer
import android.util.Base64

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
            // Determine if `licenseKey` is a URL or in the "kid:k" format
            val drmCallback = if (licenseKey.startsWith("http://") || licenseKey.startsWith("https://")) {
                // Case 1: `licenseKey` is a URL, use it directly as DRM license server URL
                HttpMediaDrmCallback(licenseKey, DefaultHttpDataSource.Factory())
            } else {
                // Case 2: `licenseKey` is in "kid:k" format, parse and base64 URL-encode it correctly
                val licenseParts = licenseKey.split(":")
                if (licenseParts.size == 2) {
                    // Parse `kid` and `k` as hex and convert them to base64 URL without padding
                    val kidBytes = hexStringToByteArray(licenseParts[0])
                    val keyBytes = hexStringToByteArray(licenseParts[1])

                    val kidBase64 = toBase64UrlWithoutPadding(kidBytes)
                    val keyBase64 = toBase64UrlWithoutPadding(keyBytes)

                    // Create JSON for ClearKey DRM with base64 URL encoded values
                    val keyString = """{"keys":[{"kty":"oct","k":"$keyBase64","kid":"$kidBase64"}],"type":"temporary"}"""

                    // Create DRM callback with generated JSON
                    LocalMediaDrmCallback(keyString.encodeToByteArray())
                } else {
                    Log.d(TAG, "Invalid licenseKey format: $licenseKey")
                    return
                }
            }

            try {
                // Use `videoResource` as the video resource URL
                setMediaSource(
                    DashMediaSource.Factory(DefaultHttpDataSource.Factory())
                        .setDrmSessionManagerProvider {
                            DefaultDrmSessionManager.Builder()
                                .setPlayClearSamplesWithoutKeys(true)  // Allow handling of unencrypted samples
                                .setMultiSession(true)
                                .setKeyRequestParameters(emptyMap())
                                .setUuidAndExoMediaDrmProvider(
                                    C.CLEARKEY_UUID,
                                    FrameworkMediaDrm.DEFAULT_PROVIDER
                                )
                                .build(drmCallback)
                        }
                        .createMediaSource(MediaItem.fromUri(videoResource))
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

    // Function to convert hex string to byte array
    private fun hexStringToByteArray(hex: String): ByteArray {
        val len = hex.length
        val data = ByteArray(len / 2)
        for (i in 0 until len step 2) {
            data[i / 2] = ((Character.digit(hex[i], 16) shl 4) + Character.digit(hex[i + 1], 16)).toByte()
        }
        return data
    }

    // Function to convert byte array to base64 URL without padding
    private fun toBase64UrlWithoutPadding(bytes: ByteArray): String =
        Base64.encodeToString(bytes, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
}