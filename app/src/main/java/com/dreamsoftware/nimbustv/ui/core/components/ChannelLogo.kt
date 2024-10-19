package com.dreamsoftware.nimbustv.ui.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvAsyncImage
import com.dreamsoftware.nimbustv.R

@Composable
fun ChannelLogo(
    modifier: Modifier = Modifier,
    size: Dp,
    logo: String?,
    borderColor: Color? = null
) {
    with(MaterialTheme.colorScheme) {
        FudgeTvAsyncImage(
            modifier = modifier
                .size(size)
                .clip(CircleShape)
                .background(background)
                .border(2.dp, borderColor ?: onPrimaryContainer, CircleShape),
            context = LocalContext.current,
            contentScale = ContentScale.Fit,
            defaultImagePlaceholderRes = R.drawable.main_logo,
            imageUrl = logo
        )
    }
}