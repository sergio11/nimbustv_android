package com.dreamsoftware.nimbustv.ui.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum

@Composable
fun CommonSelectableItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    titleText: String? = null,
    subtitleText: String? = null,
    onItemSelected: () -> Unit
) {
    with(MaterialTheme.colorScheme) {
        CommonListItem(modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp),
            focusedBorderColor = primaryContainer,
            borderColor = onPrimaryContainer,
            isSelected = isSelected,
            onClicked = onItemSelected
        ) { isFocused ->
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val textColor = with(MaterialTheme.colorScheme) {
                    if (isFocused || isSelected) {
                        primary
                    } else {
                        onPrimaryContainer
                    }
                }
                FudgeTvText(
                    type = FudgeTvTextTypeEnum.BODY_LARGE,
                    titleText = titleText,
                    textAlign = TextAlign.Center,
                    textBold = true,
                    maxLines = 2,
                    textColor = textColor
                )
                FudgeTvText(
                    type = FudgeTvTextTypeEnum.BODY_SMALL,
                    titleText = subtitleText,
                    textAlign = TextAlign.Center,
                    singleLine = true,
                    textColor = textColor
                )
            }
        }
    }
}