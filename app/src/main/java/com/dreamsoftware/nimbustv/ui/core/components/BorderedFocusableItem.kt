package com.dreamsoftware.nimbustv.ui.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Border
import androidx.tv.material3.ClickableSurfaceBorder
import androidx.tv.material3.ClickableSurfaceColors
import androidx.tv.material3.ClickableSurfaceDefaults
import androidx.tv.material3.ClickableSurfaceScale
import androidx.tv.material3.ClickableSurfaceShape
import androidx.tv.material3.Surface
import androidx.tv.material3.Text

@Composable
fun BorderedFocusableItem(
    modifier: Modifier = Modifier,
    borderRadius: Dp = 12.dp,
    scale: ClickableSurfaceScale = ClickableSurfaceDefaults.scale(focusedScale = 1.1f),
    color: ClickableSurfaceColors = ClickableSurfaceDefaults.colors(
        containerColor = colorScheme.primaryContainer,
        focusedContainerColor = colorScheme.onPrimaryContainer,
        contentColor = colorScheme.secondary,
        focusedContentColor = colorScheme.secondary
    ),
    border: ClickableSurfaceBorder = ClickableSurfaceDefaults.border(
        focusedBorder = Border(
            BorderStroke(
                width = 2.dp,
                color = colorScheme.primaryContainer
            ),
            shape = RoundedCornerShape(borderRadius)
        )
    ),
    shape: ClickableSurfaceShape = ClickableSurfaceDefaults.shape(
        shape = RoundedCornerShape(borderRadius),
        focusedShape = RoundedCornerShape(borderRadius)
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
    content: @Composable (BoxScope.() -> Unit)
) {
    Surface(
        onClick = { onClick() },
        scale = scale,
        colors = color,
        border = border,
        shape = shape,
        interactionSource = interactionSource,
        modifier = modifier
            .fillMaxWidth()
    ) {
        content()
    }
}

@Preview
@Composable
private fun BorderedFocusableItemPrev() {
    BorderedFocusableItem(onClick = {}) {
        Text(text = "Preview Text")
    }
}