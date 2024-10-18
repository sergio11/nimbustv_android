package com.dreamsoftware.nimbustv.ui.screens.home.components.importer

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.GetApp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
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
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(8.dp),
                    color = primary
                )
            } else {
                FudgeTvTextField(
                    value = playlistAlias,
                    onValueChange = onPlayListAliasUpdated,
                    labelRes = R.string.home_screen_import_new_iptv_playlist_text_field_alias_label,
                    icon = Icons.Default.Edit
                )
                FudgeTvTextField(
                    value = playListUrl,
                    onValueChange = onPlayListUrlUpdated,
                    labelRes = R.string.home_screen_import_new_iptv_playlist_text_field_url_label,
                    icon = Icons.Default.GetApp
                )
            }
        }
    }
}