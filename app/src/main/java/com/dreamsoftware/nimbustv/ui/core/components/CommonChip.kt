package com.dreamsoftware.nimbustv.ui.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Border
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.FilterChip
import androidx.tv.material3.FilterChipDefaults
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun CommonChip(
    isSelected: Boolean,
    text: String,
    onSelected: () -> Unit = {}
) {
    with(MaterialTheme.colorScheme) {
        val interactionSource = remember { MutableInteractionSource() }
        interactionSource.collectIsHoveredAsState()
        val isFocused by interactionSource.collectIsFocusedAsState()
        val isPressed by interactionSource.collectIsPressedAsState()
        FilterChip(
            onClick = onSelected,
            selected = isSelected,
            interactionSource = interactionSource,
            colors = FilterChipDefaults.colors(
                containerColor = primaryContainer,
                selectedContainerColor = onPrimaryContainer,
                focusedContainerColor = onPrimaryContainer,
            ),
            border = FilterChipDefaults.border(
                border = Border(
                    border = BorderStroke(
                        width = 2.dp,
                        color = onPrimary
                    ),
                    shape = FilterChipDefaults.ContainerShape
                ),
                focusedBorder = Border(
                    border = BorderStroke(
                        width = 2.dp,
                        color = primary
                    ),
                    shape = FilterChipDefaults.ContainerShape
                ),
                selectedBorder = Border(
                    border = BorderStroke(
                        width = 2.dp,
                        color = primary
                    ),
                    shape = FilterChipDefaults.ContainerShape
                ),
                focusedSelectedBorder = Border(
                    border = BorderStroke(
                        width = 2.dp,
                        color = primary
                    ),
                    shape = FilterChipDefaults.ContainerShape
                )
            )
        ) {
            FudgeTvText(
                type = FudgeTvTextTypeEnum.LABEL_MEDIUM,
                titleText = text,
                textColor = if(isFocused || isPressed || isSelected) {
                    primary
                } else {
                    onPrimaryContainer
                }
            )
        }
    }
}