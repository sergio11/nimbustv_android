package com.dreamsoftware.nimbustv.ui.screens.epgsources

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dreamsoftware.fudge.component.FudgeTvButton
import com.dreamsoftware.fudge.component.FudgeTvButtonStyleTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvButtonTypeEnum
import com.dreamsoftware.fudge.component.FudgeTvDialog
import com.dreamsoftware.fudge.component.FudgeTvFocusRequester
import com.dreamsoftware.fudge.component.FudgeTvLazyVerticalGrid
import com.dreamsoftware.fudge.component.FudgeTvLoadingState
import com.dreamsoftware.fudge.component.FudgeTvScreenContent
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.fudge.utils.conditional
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.model.EpgBO
import com.dreamsoftware.nimbustv.ui.core.components.CommonSelectableItem
import com.dreamsoftware.nimbustv.ui.core.components.ImportEpgDataDialog
import com.dreamsoftware.nimbustv.ui.screens.epg.components.NoEpgDataFound
import com.dreamsoftware.nimbustv.ui.screens.onboarding.playSoundEffectOnFocus

@Composable
internal fun EpgSourcesScreenContent(
    uiState: EpgSourcesUiState,
    actionListener: EpgSourcesScreenActionListener
) {
    with(uiState) {
        with(actionListener) {
            ImportEpgDataDialog(
                isVisible = isImportEpgDialogVisible,
                isImporting = isImporting,
                epgUrl = newEpgUrl,
                epgAlias = newEpgAlias,
                onEpgUrlUpdated = ::onNewEpgUrlUpdated,
                onEpgAliasUpdated = ::onNewEpgAliasUpdated,
                onAcceptClicked = ::onImportNewEpgConfirmed,
                onCancelClicked = ::onImportNewEpgCancelled
            )
            FudgeTvDialog(
                isVisible = showDeleteEpgDialog,
                mainLogoRes = R.drawable.main_logo,
                titleRes = R.string.epg_sources_screen_delete_epg_dialog_title,
                descriptionRes = R.string.epg_sources_screen_delete_epg_dialog_description,
                successRes = R.string.epg_sources_screen_delete_epg_dialog_accept_button_text,
                cancelRes = R.string.epg_sources_screen_delete_epg_dialog_cancel_button_text,
                onAcceptClicked = ::onDeleteEpgConfirmed,
                onCancelClicked = ::onDeleteEpgCancelled
            )
            FudgeTvScreenContent(
                error = errorMessage,
                onErrorAccepted = ::onErrorMessageCleared
            ) {
                when {
                    isLoading -> {
                        FudgeTvLoadingState(modifier = Modifier.fillMaxSize())
                    }

                    epgSources.isEmpty() -> {
                        NoEpgDataFound(onImportClicked = ::onImportNewEpgClicked)
                    }
                    else -> {
                        EpgSourcesMainContent(
                            data = epgSources,
                            actionListener = actionListener
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EpgSourcesMainContent(
    data: List<EpgBO>,
    actionListener: EpgSourcesScreenActionListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        EpgSourcesHeader(actionListener = actionListener)
        EpgSourcesGridContent(
            data = data,
            onItemClicked = actionListener::onDeleteEpgClicked
        )
    }
}

@Composable
private fun EpgSourcesHeader(actionListener: EpgSourcesScreenActionListener) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .padding(end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FudgeTvText(
            type = FudgeTvTextTypeEnum.HEADLINE_MEDIUM,
            titleRes = R.string.epg_sources_screen_title,
            textBold = true
        )
        FudgeTvButton(
            modifier = Modifier
                .width(200.dp)
                .playSoundEffectOnFocus(),
            type = FudgeTvButtonTypeEnum.SMALL,
            style = FudgeTvButtonStyleTypeEnum.TRANSPARENT,
            textRes = R.string.epg_sources_screen_import_new_epg_button_text,
            onClick = actionListener::onImportNewEpgClicked
        )
    }
}

@Composable
private fun EpgSourcesGridContent(
    data: List<EpgBO>,
    onItemClicked: (EpgBO) -> Unit
) {
    FudgeTvFocusRequester { focusRequester ->
        FudgeTvLazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            state = rememberLazyGridState(),
            items = data
        ) { idx, item ->
            CommonSelectableItem(
                modifier = Modifier.conditional(idx == 0, ifTrue = {
                    focusRequester(focusRequester)
                }),
                titleText = item.alias,
                subtitleText = stringResource(id = R.string.epg_screen_item_channels_count_text, item.channelsCount),
                onItemSelected = { onItemClicked(item) }
            )
        }
    }
}