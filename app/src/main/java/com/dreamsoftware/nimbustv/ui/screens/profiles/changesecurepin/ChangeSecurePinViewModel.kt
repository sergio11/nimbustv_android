package com.dreamsoftware.nimbustv.ui.screens.profiles.changesecurepin

import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.di.ChangeSecurePinScreenErrorMapper
import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.domain.usecase.ChangeSecurePinUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetProfileByIdUseCase
import com.dreamsoftware.nimbustv.ui.utils.EMPTY
import com.dreamsoftware.nimbustv.utils.combinedLet
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangeSecurePinViewModel @Inject constructor(
    private val getProfileByIdUseCase: GetProfileByIdUseCase,
    private val changeSecurePinUseCase: ChangeSecurePinUseCase,
    @ChangeSecurePinScreenErrorMapper private val errorMapper: IFudgeTvErrorMapper
): FudgeTvViewModel<ChangeSecurePinUiState, ChangeSecurePinSideEffects>(), ChangeSecurePinActionListener {

    override fun onGetDefaultState(): ChangeSecurePinUiState = ChangeSecurePinUiState()

    fun load(profileId: String) {
        executeUseCaseWithParams(
            useCase = getProfileByIdUseCase,
            params = GetProfileByIdUseCase.Params(profileId),
            onSuccess = ::onLoadProfileCompleted,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    private fun onLoadProfileCompleted(profileBO: ProfileBO) {
        updateState { it.copy(profile = profileBO) }
    }

    override fun onConfirmPressed() {
        uiState.value.let {
            combinedLet(it.currentSecurePin.toIntOrNull(), it.newSecurePin.toIntOrNull()) { currentSecurePin, newSecurePin ->
                executeUseCaseWithParams(
                    useCase = changeSecurePinUseCase,
                    params = ChangeSecurePinUseCase.Params(
                        profileId = it.profile?.id.orEmpty(),
                        currentSecurePin = currentSecurePin,
                        newSecurePin = newSecurePin
                    ),
                    onSuccess = { onSecurePinChanged() },
                    onMapExceptionToState = ::onMapExceptionToState
                )
            }
        }
    }

    override fun onDeleteProfilePressed() {
        uiState.value.profile?.let {
            launchSideEffect(ChangeSecurePinSideEffects.RequestDeleteProfile(it.id))
        }
    }

    override fun onCurrentSecurePinChanged(pin: String) {
        updateState { it.copy(currentSecurePin = pin) }
    }

    override fun onNewSecurePinChanged(pin: String) {
        updateState { it.copy(newSecurePin = pin) }
    }

    override fun onCloseSecurePinUpdatedDialog() {
        updateState { it.copy(showSecurePinUpdatedDialog = false) }
        launchSideEffect(ChangeSecurePinSideEffects.SecurePinUpdated)
    }

    private fun onSecurePinChanged() {
        updateState {
            it.copy(
                showSecurePinUpdatedDialog = true,
                currentSecurePinError = String.EMPTY,
                currentSecurePin = String.EMPTY,
                newSecurePin = String.EMPTY,
                newSecurePinError = String.EMPTY
            )
        }
    }

    private fun onMapExceptionToState(ex: Exception, uiState: ChangeSecurePinUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )
}

data class ChangeSecurePinUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val showSecurePinUpdatedDialog: Boolean = false,
    val profile: ProfileBO? = null,
    val currentSecurePin: String = String.EMPTY,
    val currentSecurePinError: String = String.EMPTY,
    val newSecurePin: String = String.EMPTY,
    val newSecurePinError: String = String.EMPTY,
): UiState<ChangeSecurePinUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): ChangeSecurePinUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface ChangeSecurePinSideEffects: SideEffect {
    data class RequestDeleteProfile(val id: String): ChangeSecurePinSideEffects
    data object SecurePinUpdated: ChangeSecurePinSideEffects
}