package com.dreamsoftware.nimbustv.ui.screens.epg.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GetApp
import androidx.compose.runtime.Composable
import com.dreamsoftware.fudge.component.FudgeTvDialog
import com.dreamsoftware.fudge.component.FudgeTvTextField
import com.dreamsoftware.nimbustv.R

@Composable
internal fun ImportEpgDataDialog(
    isVisible: Boolean,
    epgDataUrl: String,
    onEpgDataUrlUpdated: (String) -> Unit,
    onAcceptClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {
    FudgeTvDialog(
        isVisible = isVisible,
        mainLogoRes = R.drawable.main_logo,
        titleRes = R.string.epg_screen_import_new_epg_data_dialog_title,
        descriptionRes = R.string.epg_screen_import_new_epg_data_dialog_description,
        successRes = R.string.epg_screen_import_new_epg_data_dialog_accept_button_text,
        cancelRes = R.string.epg_screen_import_new_epg_data_dialog_cancel_button_text,
        onAcceptClicked = onAcceptClicked,
        onCancelClicked = onCancelClicked
    ) {
        FudgeTvTextField(
            value = epgDataUrl,
            onValueChange = onEpgDataUrlUpdated,
            labelRes = R.string.epg_screen_import_new_epg_data_text_field_url_label,
            icon = Icons.Default.GetApp
        )
    }
}