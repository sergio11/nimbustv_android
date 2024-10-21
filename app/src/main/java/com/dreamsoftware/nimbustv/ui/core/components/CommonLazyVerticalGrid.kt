package com.dreamsoftware.nimbustv.ui.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.common.collect.Iterables

@Composable
fun <T: Any>CommonLazyVerticalGrid(
    modifier: Modifier = Modifier,
    state: LazyGridState,
    items: Iterable<T>,
    onBuildContent: @Composable LazyGridItemScope.(Int, item: T) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 150.dp),
        state = state,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(Iterables.size(items)) { index ->
            onBuildContent(index, Iterables.get(items, index))
        }
    }
}