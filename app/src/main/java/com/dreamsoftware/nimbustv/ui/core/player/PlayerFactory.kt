package com.dreamsoftware.nimbustv.ui.core.player

import android.content.Context
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.util.EventLogger
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.dreamsoftware.nimbustv.ui.core.player.impl.ExoPlayerImpl

@UnstableApi object PlayerFactory {

    fun create(
        context: Context
    ): SupportPlayer {
        val exoPlayer = ExoPlayer.Builder(context)
            .setRenderersFactory(DefaultRenderersFactory(context).setEnableDecoderFallback(true))
            .build()
            .apply {
                addAnalyticsListener(EventLogger())
                repeatMode = Player.REPEAT_MODE_ALL
            }
        return ExoPlayerImpl(
            exoPlayer
        ) {
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                hideController()
            }
        }
    }
}