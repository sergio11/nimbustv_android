package com.dreamsoftware.nimbustv.ui.screens.profiles.delete

import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeleteProfileViewModel @Inject constructor(): FudgeTvViewModel<DeleteProfileUiState, DeleteProfileSideEffects>(), DeleteProfileScreenActionListener {

    override fun onGetDefaultState(): DeleteProfileUiState = DeleteProfileUiState()

    fun load(profileId: String) {
    }

    private fun onLoadProfileCompleted(profileBO: ProfileBO) {
        updateState {
            it.copy(profile = profileBO)
        }
    }

    private fun onProfileDeleted() {
        updateState { it.copy(showProfileDeletedDialog = true) }
    }

    override fun onDeletePressed() {
        with(uiState.value) {
            profile?.let {}
        }
    }

    override fun onCancelPressed() {
        launchSideEffect(DeleteProfileSideEffects.CancelConfiguration)
    }

    override fun onProfileDeletedDialogClosed() {
        updateState { it.copy(showProfileDeletedDialog = false) }
        launchSideEffect(DeleteProfileSideEffects.ProfileDeleteSuccessfully)
    }
}

data class DeleteProfileUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val showProfileDeletedDialog: Boolean = false,
    val profile: ProfileBO? = null
): UiState<DeleteProfileUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): DeleteProfileUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface DeleteProfileSideEffects: SideEffect {
    data object ProfileDeleteSuccessfully: DeleteProfileSideEffects
    data object CancelConfiguration: DeleteProfileSideEffects
}