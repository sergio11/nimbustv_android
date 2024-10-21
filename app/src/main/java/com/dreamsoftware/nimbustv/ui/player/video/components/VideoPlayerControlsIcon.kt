package com.dreamsoftware.nimbustv.ui.player.video.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ClickableSurfaceDefaults
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import com.dreamsoftware.nimbustv.ui.core.player.PlayerControlsState

@Composable
fun VideoPlayerControlsIcon(
    modifier: Modifier = Modifier,
    state: PlayerControlsState,
    size: Dp = 40.dp,
    isPlaying: Boolean,
    @DrawableRes icon: Int,
    contentDescription: String? = null,
    onClick: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused && isPlaying) {
        if (isFocused && isPlaying) {
            state.showControls()
        }
    }

    with(MaterialTheme.colorScheme) {
        Surface(
            modifier = modifier.size(size),
            onClick = onClick,
            shape = ClickableSurfaceDefaults.shape(shape = CircleShape),
            colors = ClickableSurfaceDefaults.colors(
                focusedContainerColor = primary.copy(alpha = 0.8f),
                containerColor = primary,
            ),
            scale = ClickableSurfaceDefaults.scale(focusedScale = 1.05f),
            interactionSource = interactionSource,
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                painter = painterResource(id = icon),
                contentDescription = contentDescription,
                tint = onPrimary,
            )
        }
    }
}