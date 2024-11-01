package com.dreamsoftware.nimbustv.ui.core.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.GetApp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import com.dreamsoftware.fudge.component.FudgeTvDialog
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextField
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.nimbustv.R

@Composable
fun ImportPlaylistDialog(
    isVisible: Boolean,
    playlistAlias: String,
    playListUrl: String,
    isImporting: Boolean,
    onPlayListAliasUpdated: (String) -> Unit,
    onPlayListUrlUpdated: (String) -> Unit,
    onAcceptClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {
    with(MaterialTheme.colorScheme) {
        FudgeTvDialog(
            isVisible = isVisible,
            mainLogoRes = R.drawable.main_logo,
            titleRes = R.string.home_screen_import_new_iptv_playlist_dialog_title,
            descriptionRes = R.string.home_screen_import_new_iptv_playlist_dialog_description,
            successRes = R.string.home_screen_import_new_iptv_playlist_dialog_accept_button_text,
            cancelRes = R.string.home_screen_import_new_iptv_playlist_dialog_cancel_button_text,
            onAcceptClicked = onAcceptClicked.takeUnless { isImporting },
            onCancelClicked = onCancelClicked.takeUnless { isImporting }
        ) {
            if(isImporting) {
                FudgeTvText(
                    type = FudgeTvTextTypeEnum.TITLE_LARGE,
                    titleRes = R.string.home_screen_import_new_iptv_playlist_dialog_in_progress_text,
                    textColor = primary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(5.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(55.dp)
                        .padding(8.dp),
                    color = primary
                )
            } else {
                val playlistAliasInteractionSource = remember { MutableInteractionSource() }
                val isPlaylistAliasFocused by playlistAliasInteractionSource.collectIsFocusedAsState()
                if(!isPlaylistAliasFocused) {
                    FudgeTvTextField(
                        modifier = Modifier.width(350.dp),
                        value = playListUrl,
                        onValueChange = onPlayListUrlUpdated,
                        labelRes = R.string.home_screen_import_new_iptv_playlist_text_field_url_label,
                        icon = Icons.Default.GetApp,
                        focusedTextColor = primary,
                        unfocusedTextColor = primary,
                        unfocusedLabelColor = primary,
                        focusedLabelColor = primaryContainer,
                        focusedBorderColor = primary,
                        unfocusedBorderColor = primaryContainer,
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
                FudgeTvTextField(
                    modifier = Modifier.width(350.dp),
                    value = playlistAlias,
                    onValueChange = onPlayListAliasUpdated,
                    labelRes = R.string.home_screen_import_new_iptv_playlist_text_field_alias_label,
                    icon = Icons.Default.Edit,
                    focusedTextColor = primary,
                    unfocusedTextColor = primary,
                    unfocusedLabelColor = primary,
                    focusedLabelColor = primaryContainer,
                    focusedBorderColor = primary,
                    unfocusedBorderColor = primaryContainer,
                    interactionSource = playlistAliasInteractionSource,
                    singleLine = true
                )
            }
        }
    }
}