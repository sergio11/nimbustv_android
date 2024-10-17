package com.dreamsoftware.nimbustv.ui.screens.epg.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.MaterialTheme

@Composable
internal fun EpgProgrammeCell(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit) ? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .padding(4.dp),
        border = CardDefaults.border(
            focusedBorder =
            Border(
                border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.onPrimaryContainer),
                shape = RoundedCornerShape(8.dp)
            )
        ),
        colors = CardDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = {
            onClick?.invoke()
        }
    ) {
        content()
    }
}