package com.dreamsoftware.nimbustv.ui.screens.profiles.secure

import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.di.SecurePinScreenErrorMapper
import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.domain.usecase.GetProfileByIdUseCase
import com.dreamsoftware.nimbustv.domain.usecase.SelectProfileUseCase
import com.dreamsoftware.nimbustv.domain.usecase.VerifyPinUseCase
import com.dreamsoftware.nimbustv.ui.utils.EMPTY
import com.dreamsoftware.nimbustv.utils.combinedLet
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SecurePinViewModel @Inject constructor(
    private val verifyPinUseCase: VerifyPinUseCase,
    private val selectProfileUseCase: SelectProfileUseCase,
    private val getProfileByIdUseCase: GetProfileByIdUseCase,
    @SecurePinScreenErrorMapper private val errorMapper: IFudgeTvErrorMapper
): FudgeTvViewModel<SecurePinUiState, SecurePinSideEffects>(), SecurePinScreenActionListener {

    override fun onGetDefaultState(): SecurePinUiState = SecurePinUiState()

    fun load(profileId: String) {
        executeUseCaseWithParams(
            useCase = getProfileByIdUseCase,
            params = GetProfileByIdUseCase.Params(profileId),
            onSuccess = ::onLoadProfileCompleted,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    override fun onUnlockPinChanged(unlockPin: String) {
        updateState {
            it.copy(unlockPin = unlockPin)
        }
    }

    override fun onVerifyPressed() {
        with(uiState.value) {
            combinedLet(profileLocked, unlockPin.toIntOrNull()) { profile, pin ->
                executeUseCaseWithParams(
                    useCase = verifyPinUseCase,
                    params = VerifyPinUseCase.Params(profileId = profile.id, pin = pin),
                    onSuccess = { onVerifyPinSuccessfully(profile) },
                    onMapExceptionToState = ::onMapExceptionToState
                )
            }
        }
    }

    override fun onCancelPressed() {
        launchSideEffect(SecurePinSideEffects.CancelVerification)
    }

    private fun onLoadProfileCompleted(profileBO: ProfileBO) {
        updateState {
            it.copy(profileLocked = profileBO)
        }
    }

    private fun onVerifyPinSuccessfully(profile: ProfileBO) {
        executeUseCaseWithParams(
            useCase = selectProfileUseCase,
            params = SelectProfileUseCase.Params(profile),
            onSuccess = { onProfileSelected() },
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    private fun onProfileSelected() {
        launchSideEffect(SecurePinSideEffects.ProfileUnlockedSuccessfully)
    }

    private fun onMapExceptionToState(ex: Exception, uiState: SecurePinUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )
}

data class SecurePinUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val unlockPin: String = String.EMPTY,
    val profileLocked: ProfileBO? = null
): UiState<SecurePinUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): SecurePinUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface SecurePinSideEffects: SideEffect {
    data object ProfileUnlockedSuccessfully: SecurePinSideEffects
    data object CancelVerification: SecurePinSideEffects
}