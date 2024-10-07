package com.dreamsoftware.nimbustv.ui.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Border
import androidx.tv.material3.ClickableSurfaceDefaults
import androidx.tv.material3.MaterialTheme.colorScheme

@Composable
fun CommonListItem(
    modifier: Modifier,
    onClicked: () -> Unit,
    onFocused: () -> Unit = {},
    containerColor: Color? = null,
    focusedContainerColor: Color? = null,
    borderColor: Color? = null,
    focusedBorderColor: Color? = null,
    isSelected: Boolean = false,
    content: @Composable (isFocused: Boolean) -> Unit
) {
    with(colorScheme) {
        val interactionSource = remember { MutableInteractionSource() }
        val isFocused by interactionSource.collectIsFocusedAsState()
        if(isFocused) {
            onFocused()
        }
        BorderedFocusableItem(
            onClick = onClicked,
            color = ClickableSurfaceDefaults.colors(
                containerColor = containerColor ?: if(isSelected) onPrimaryContainer else primaryContainer,
                focusedContainerColor = focusedContainerColor ?: onPrimaryContainer
            ),
            border = ClickableSurfaceDefaults.border(
                border = Border(
                    BorderStroke(
                        width = 2.dp,
                        color = if(isFocused || isSelected) {
                            focusedBorderColor ?: primaryContainer
                        } else {
                            borderColor ?: onPrimaryContainer
                        }
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            ),
            interactionSource = interactionSource,
            modifier = modifier,
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                content(isFocused)
            }
        }
    }
}