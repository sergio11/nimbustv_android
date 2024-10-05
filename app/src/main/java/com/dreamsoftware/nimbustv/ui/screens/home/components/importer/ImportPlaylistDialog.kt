package com.dreamsoftware.nimbustv.ui.screens.home.components.importer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GetApp
import androidx.compose.runtime.Composable
import com.dreamsoftware.fudge.component.FudgeTvDialog
import com.dreamsoftware.fudge.component.FudgeTvTextField
import com.dreamsoftware.nimbustv.R

@Composable
fun ImportPlaylistDialog(
    isVisible: Boolean,
    playListUrl: String,
    onPlayListUrlUpdated: (String) -> Unit,
    onAcceptClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {
    FudgeTvDialog(
        isVisible = isVisible,
        mainLogoRes = R.drawable.main_logo,
        titleRes = R.string.home_screen_import_new_iptv_playlist_dialog_title,
        descriptionRes = R.string.home_screen_import_new_iptv_playlist_dialog_description,
        successRes = R.string.home_screen_import_new_iptv_playlist_dialog_accept_button_text,
        cancelRes = R.string.home_screen_import_new_iptv_playlist_dialog_cancel_button_text,
        onAcceptClicked = onAcceptClicked,
        onCancelClicked = onCancelClicked
    ) {
        FudgeTvTextField(
            value = playListUrl,
            onValueChange = onPlayListUrlUpdated,
            labelRes = R.string.home_screen_import_new_iptv_playlist_text_field_url_label,
            icon = Icons.Default.GetApp
        )
    }
}