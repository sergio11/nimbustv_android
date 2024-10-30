package com.dreamsoftware.nimbustv.ui.core.components

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
internal fun ImportEpgDataDialog(
    isVisible: Boolean,
    isImporting: Boolean,
    epgUrl: String,
    epgAlias: String,
    onEpgAliasUpdated: (String) -> Unit,
    onEpgUrlUpdated: (String) -> Unit,
    onAcceptClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {
    with(MaterialTheme.colorScheme) {
        FudgeTvDialog(
            isVisible = isVisible,
            mainLogoRes = R.drawable.main_logo,
            titleRes = R.string.epg_screen_import_new_epg_data_dialog_title,
            descriptionRes = R.string.epg_screen_import_new_epg_data_dialog_description,
            successRes = R.string.epg_screen_import_new_epg_data_dialog_accept_button_text,
            cancelRes = R.string.epg_screen_import_new_epg_data_dialog_cancel_button_text,
            onAcceptClicked = onAcceptClicked.takeUnless { isImporting },
            onCancelClicked = onCancelClicked.takeUnless { isImporting }
        ) {
            if(isImporting) {
                FudgeTvText(
                    type = FudgeTvTextTypeEnum.TITLE_LARGE,
                    titleRes = R.string.epg_screen_import_new_epg_dialog_in_progress_text,
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
                FudgeTvTextField(
                    modifier = Modifier.width(350.dp),
                    value = epgUrl,
                    onValueChange = onEpgUrlUpdated,
                    labelRes = R.string.epg_screen_import_new_epg_data_text_field_url_label,
                    icon = Icons.Default.GetApp,
                    unfocusedLabelColor = primary,
                    focusedLabelColor = primaryContainer,
                    focusedBorderColor = primary,
                    unfocusedBorderColor = primaryContainer,
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(5.dp))
                FudgeTvTextField(
                    modifier = Modifier.width(350.dp),
                    value = epgAlias,
                    onValueChange = onEpgAliasUpdated,
                    labelRes = R.string.epg_screen_import_new_epg_data_text_field_alias_label,
                    icon = Icons.Default.Edit,
                    unfocusedLabelColor = primary,
                    focusedLabelColor = primaryContainer,
                    focusedBorderColor = primary,
                    unfocusedBorderColor = primaryContainer,
                    singleLine = true
                )
            }
        }
    }
}