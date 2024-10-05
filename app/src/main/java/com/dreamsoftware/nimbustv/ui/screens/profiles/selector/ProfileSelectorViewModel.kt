package com.dreamsoftware.nimbustv.ui.screens.profiles.selector

import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.ui.utils.toDrawableResource
import com.dreamsoftware.fudge.component.profiles.ProfileSelectorVO
import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.domain.usecase.GetProfilesUseCase
import com.dreamsoftware.nimbustv.domain.usecase.SelectProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileSelectorViewModel @Inject constructor(
    private val getProfilesUseCase: GetProfilesUseCase,
    private val selectProfileUseCase: SelectProfileUseCase,
): FudgeTvViewModel<ProfileSelectorUiState, ProfileSelectorSideEffects>(), ProfileSelectorScreenActionListener {

    private var userProfiles: List<ProfileBO> = emptyList()

    override fun onGetDefaultState(): ProfileSelectorUiState = ProfileSelectorUiState()

    fun loadProfiles() {
        executeUseCase(
            useCase = getProfilesUseCase,
            onSuccess = ::onLoadProfileSuccessfully
        )
    }

    override fun onProfileSelected(profileId: String) {
        userProfiles.find { it.id == profileId }?.let { profile ->
            updateState {
                it.copy(profileSelected = profile)
            }
            onProfileLocked(profile.id)
        }
    }

    override fun onAddProfilePressed() {
        launchSideEffect(ProfileSelectorSideEffects.AddNewProfile)
    }

    override fun onProfileManagementPressed() {
        launchSideEffect(ProfileSelectorSideEffects.ConfigureProfiles)
    }

    private fun onLoadProfileSuccessfully(profiles: List<ProfileBO>) {
        userProfiles = profiles
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

    private fun onProfileLocked(profileId: String) {
        launchSideEffect(ProfileSelectorSideEffects.ProfileLocked(profileId))
    }

    private fun onProfileSelected() {
        launchSideEffect(ProfileSelectorSideEffects.ProfileSelected)
    }

    private fun selectProfile(profileBO: ProfileBO) {
        executeUseCaseWithParams(
            useCase = selectProfileUseCase,
            params = SelectProfileUseCase.Params(profileBO),
            onSuccess = {
                onProfileSelected()
            }
        )
    }
}

data class ProfileSelectorUiState(
    override var isLoading: Boolean = false,
    override var errorMessage: String? = null,
    val profiles: List<ProfileSelectorVO> = emptyList(),
    val profileSelected: ProfileBO? = null
): UiState<ProfileSelectorUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): ProfileSelectorUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface ProfileSelectorSideEffects: SideEffect {
    data object ProfileSelected: ProfileSelectorSideEffects
    data class ProfileLocked(val profileId: String): ProfileSelectorSideEffects
    data object AddNewProfile: ProfileSelectorSideEffects
    data object ConfigureProfiles: ProfileSelectorSideEffects
}