package com.dreamsoftware.nimbustv.ui.screens.epg.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LiveTv
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvButton
import com.dreamsoftware.fudge.component.FudgeTvButtonTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvFocusRequester
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.fudge.component.fudgeTvPlaySoundEffectOnFocus
import com.dreamsoftware.nimbustv.R

@Composable
internal fun NoEpgDataFound(onImportClicked: () -> Unit) {
    FudgeTvFocusRequester { focusRequester ->
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Rounded.LiveTv,
                modifier = Modifier.size(64.dp),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(30.dp))
            FudgeTvText(
                titleRes = R.string.epg_screen_no_epg_data_found_text,
                type = FudgeTvTextTypeEnum.LABEL_LARGE,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
            FudgeTvButton(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fudgeTvPlaySoundEffectOnFocus(),
                type = FudgeTvButtonTypeEnum.MEDIUM,
                onClick = onImportClicked,
                textRes = R.string.epg_screen_import_new_epg_data_button_text,
            )
        }
    }
}


