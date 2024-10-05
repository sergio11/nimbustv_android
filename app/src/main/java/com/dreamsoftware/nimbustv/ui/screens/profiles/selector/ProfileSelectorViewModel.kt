package com.dreamsoftware.nimbustv.ui.screens.profiles.selector

import com.dreamsoftware.fudge.component.profiles.ProfileSelectorVO
import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.domain.usecase.GetProfilesUseCase
import com.dreamsoftware.nimbustv.domain.usecase.HasProfilesCountUseCase
import com.dreamsoftware.nimbustv.ui.utils.toDrawableResource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileSelectorViewModel @Inject constructor(
    private val hasProfilesCountUseCase: HasProfilesCountUseCase,
    private val getProfilesUseCase: GetProfilesUseCase
): FudgeTvViewModel<ProfileSelectorUiState, ProfileSelectorSideEffects>(), ProfileSelectorScreenActionListener {

    private var userProfiles: List<ProfileBO> = emptyList()

    override fun onGetDefaultState(): ProfileSelectorUiState = ProfileSelectorUiState()

    fun load() {
        checkProfiles()
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
        updateState { it.copy(showNoProfilesDialog = false) }
        launchSideEffect(ProfileSelectorSideEffects.AddNewProfile)
    }

    override fun onProfileManagementPressed() {
        launchSideEffect(ProfileSelectorSideEffects.ConfigureProfiles)
    }

    override fun onCancel() {
        updateState { it.copy(showNoProfilesDialog = false) }
        launchSideEffect(ProfileSelectorSideEffects.CancelProfileSelection)
    }

    private fun loadProfiles() {
        executeUseCase(
            useCase = getProfilesUseCase,
            onSuccess = ::onLoadProfileSuccessfully
        )
    }

    private fun checkProfiles() {
        executeUseCase(
            useCase = hasProfilesCountUseCase,
            onSuccess = ::onVerifyProfilesSuccessfully
        )
    }

    private fun onVerifyProfilesSuccessfully(hasProfiles: Boolean) {
        if(hasProfiles) {
            loadProfiles()
        } else {
            updateState { it.copy(showNoProfilesDialog = true) }
        }
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
}

data class ProfileSelectorUiState(
    override var isLoading: Boolean = false,
    override var errorMessage: String? = null,
    val showNoProfilesDialog: Boolean = false,
    val profiles: List<ProfileSelectorVO> = emptyList(),
    val profileSelected: ProfileBO? = null
): UiState<ProfileSelectorUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): ProfileSelectorUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface ProfileSelectorSideEffects: SideEffect {
    data class ProfileLocked(val profileId: String): ProfileSelectorSideEffects
    data object AddNewProfile: ProfileSelectorSideEffects
    data object ConfigureProfiles: ProfileSelectorSideEffects
    data object CancelProfileSelection: ProfileSelectorSideEffects
}