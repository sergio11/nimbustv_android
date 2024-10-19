package com.dreamsoftware.nimbustv.ui.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.tv.material3.MaterialTheme
import coil.compose.AsyncImage
import com.dreamsoftware.fudge.component.FudgeTvFocusRequester
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.fudge.utils.shadowBox
import com.dreamsoftware.nimbustv.ui.theme.popupShadow
import com.dreamsoftware.nimbustv.ui.theme.surfaceContainerHigh

@Composable
fun CommonPopup(
    imageUrl: String?,
    title: String?,
    description: String?,
    onBackPressed: () -> Unit,
    content: @Composable ColumnScope.(FocusRequester) -> Unit
) {
    with(MaterialTheme.colorScheme) {
        Dialog(onDismissRequest = onBackPressed) {
            FudgeTvFocusRequester { focusRequester ->
                Box(
                    modifier = Modifier
                        .background(surfaceContainerHigh, RoundedCornerShape(16.dp))
                        .fillMaxWidth(0.75f)
                        .fillMaxHeight(0.8f)
                        .shadowBox(
                            color = popupShadow,
                            blurRadius = 40.dp,
                            offset = DpOffset(0.dp, 8.dp)
                        )
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
                            .alpha(0.88f),
                        model = imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(surfaceVariant.copy(alpha = 0.6f))
                            .padding(horizontal = 24.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Spacer(modifier = Modifier.fillMaxHeight(0.42f))
                        FudgeTvText(
                            modifier = Modifier.padding(bottom = 8.dp),
                            type = FudgeTvTextTypeEnum.HEADLINE_SMALL,
                            textColor = onSurfaceVariant,
                            titleText = title,
                            singleLine = true,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            FudgeTvText(
                                modifier = Modifier,
                                type = FudgeTvTextTypeEnum.LABEL_MEDIUM,
                                titleText = description,
                                textColor = onSurfaceVariant,
                                overflow = TextOverflow.Ellipsis,
                                softWrap = true,
                                maxLines = 4
                            )
                        }
                        content(focusRequester)
                    }
                }
            }
        }
    }
}