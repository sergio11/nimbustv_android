package com.dreamsoftware.nimbustv.ui.core.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Icon
import androidx.tv.material3.Surface

@Composable
fun CommonFavoriteButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onStateChanged: (Boolean) -> Unit,
) {
    Surface(
        shape = CircleShape,
        modifier = modifier
            .padding(12.dp)
            .size(40.dp)
    ) {
        FavoriteButton(
            modifier = Modifier.padding(8.dp),
            isFavorite = isFavorite,
            onStateChanged = onStateChanged
        )
    }
}


@Composable
private fun FavoriteButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onStateChanged: (Boolean) -> Unit
) {
    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = {
            onStateChanged(!isFavorite)
        }
    ) {
        Icon(
            tint = Color(0xffE91E63),
            modifier = modifier.graphicsLayer {
                scaleX = 1.3f
                scaleY = 1.3f
            },
            imageVector = if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = null
        )
    }
}