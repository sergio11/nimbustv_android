package com.dreamsoftware.nimbustv.ui.screens.profiles.management

import com.dreamsoftware.fudge.component.profiles.ProfileSelectorVO
import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.di.ProfileManagementScreenErrorMapper
import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.domain.usecase.GetProfilesUseCase
import com.dreamsoftware.nimbustv.ui.utils.toDrawableResource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfilesManagementViewModel @Inject constructor(
    private val getProfilesUseCase: GetProfilesUseCase,
    @ProfileManagementScreenErrorMapper private val errorMapper: IFudgeTvErrorMapper
): FudgeTvViewModel<ProfilesManagementUiState, ProfilesManagementSideEffects>() {

    override fun onGetDefaultState(): ProfilesManagementUiState = ProfilesManagementUiState()

    fun loadProfiles() {
        executeUseCase(
            useCase = getProfilesUseCase,
            onSuccess = ::onLoadProfileSuccessfully,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    private fun onLoadProfileSuccessfully(profiles: List<ProfileBO>) {
        updateState {
            it.copy(profiles = profiles.map { profile ->
                with(profile) {
                    ProfileSelectorVO(
                        uuid = id,
                        alias = alias,
                        avatarIconRes = avatarType.toDrawableResource()
                    )
                }
            })
        }
    }

    private fun onMapExceptionToState(ex: Exception, uiState: ProfilesManagementUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )
}

data class ProfilesManagementUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val profiles: List<ProfileSelectorVO> = emptyList(),
): UiState<ProfilesManagementUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): ProfilesManagementUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface ProfilesManagementSideEffects: SideEffect