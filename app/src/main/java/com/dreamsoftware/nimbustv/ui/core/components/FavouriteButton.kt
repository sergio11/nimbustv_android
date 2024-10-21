package com.dreamsoftware.nimbustv.ui.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Border
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.Icon
import androidx.tv.material3.IconButton
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.nimbustv.R

@Composable
fun FavouriteButton(
    modifier: Modifier = Modifier,
    size: Dp = 50.dp,
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    with(MaterialTheme.colorScheme) {
        val interactionSource = remember { MutableInteractionSource() }
        val isFocused by interactionSource.collectIsFocusedAsState()
        IconButton(
            onClick = onClick,
            interactionSource = interactionSource,
            modifier = modifier.size(size),
            colors = ButtonDefaults.colors(containerColor = Color.Transparent),
            border = ButtonDefaults.border(
                border = Border(
                    BorderStroke(
                        2.dp,
                        if(isFocused) {
                            border
                        } else {
                            onPrimary
                        }
                    )
                )
            )
        ) {
            Icon(
                painter = painterResource(
                    if (isFavorite)
                        R.drawable.favorite
                    else
                        R.drawable.fav_icon
                ),
                tint = if(isFocused) {
                    border
                } else {
                    onPrimary
                },
                contentDescription = null
            )
        }
    }
}