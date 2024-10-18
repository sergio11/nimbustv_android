package com.dreamsoftware.nimbustv.ui.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.VideoLibrary
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvButton
import com.dreamsoftware.fudge.component.FudgeTvButtonTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.ui.screens.onboarding.playSoundEffectOnFocus

@Composable
internal fun NoPlaylistFound(onImportClicked: () -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Rounded.VideoLibrary,
            modifier = Modifier.size(64.dp),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(30.dp))
        FudgeTvText(
            titleRes = R.string.home_screen_import_new_iptv_playlist_main_title,
            type = FudgeTvTextTypeEnum.LABEL_LARGE,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(30.dp))
        FudgeTvButton(
            modifier = Modifier.playSoundEffectOnFocus(),
            type = FudgeTvButtonTypeEnum.MEDIUM,
            onClick = onImportClicked,
            textRes = R.string.home_screen_import_new_iptv_playlist_button_text,
        )
    }
}


