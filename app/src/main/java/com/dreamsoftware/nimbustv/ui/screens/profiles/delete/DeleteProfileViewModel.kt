package com.dreamsoftware.nimbustv.ui.screens.profiles.delete

import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.di.DeleteProfileScreenErrorMapper
import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.domain.usecase.DeleteProfileUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetProfileByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeleteProfileViewModel @Inject constructor(
    private val getProfileByIdUseCase: GetProfileByIdUseCase,
    private val deleteProfileUseCase: DeleteProfileUseCase,
    @DeleteProfileScreenErrorMapper private val errorMapper: IFudgeTvErrorMapper
): FudgeTvViewModel<DeleteProfileUiState, DeleteProfileSideEffects>(), DeleteProfileScreenActionListener {

    override fun onGetDefaultState(): DeleteProfileUiState = DeleteProfileUiState()

    fun load(profileId: String) {
        executeUseCaseWithParams(
            useCase = getProfileByIdUseCase,
            params = GetProfileByIdUseCase.Params(profileId),
            onSuccess = ::onLoadProfileCompleted,
            onMapExceptionToState = ::onMapExceptionToState
        )
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
            profile?.id?.let { profileId ->
                executeUseCaseWithParams(
                    useCase = deleteProfileUseCase,
                    params = DeleteProfileUseCase.Params(profileId = profileId),
                    onSuccess = {
                        onProfileDeleted()
                    },
                    onMapExceptionToState = ::onMapExceptionToState
                )
            }
        }
    }

    override fun onCancelPressed() {
        launchSideEffect(DeleteProfileSideEffects.CancelConfiguration)
    }

    override fun onProfileDeletedDialogClosed() {
        updateState { it.copy(showProfileDeletedDialog = false) }
        launchSideEffect(DeleteProfileSideEffects.ProfileDeleteSuccessfully)
    }

    private fun onMapExceptionToState(ex: Exception, uiState: DeleteProfileUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )
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