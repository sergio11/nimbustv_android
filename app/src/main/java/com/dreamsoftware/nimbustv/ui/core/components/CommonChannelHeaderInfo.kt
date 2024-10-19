package com.dreamsoftware.nimbustv.ui.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum

@Composable
fun CommonChannelHeaderInfo(
    modifier: Modifier = Modifier,
    title: String?,
    subtitle: String?,
    logo: String?
) {
    with(MaterialTheme.colorScheme) {
        Row (modifier = modifier.height(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)  {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ChannelLogo(size = 50.dp, logo = logo)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    FudgeTvText(
                        titleText = title,
                        type = FudgeTvTextTypeEnum.TITLE_LARGE,
                        textBold = true,
                        textColor = onPrimaryContainer
                    )
                    FudgeTvText(
                        titleText = subtitle,
                        type = FudgeTvTextTypeEnum.BODY_MEDIUM,
                        textColor = onPrimaryContainer
                    )
                }
            }
        }
    }
}