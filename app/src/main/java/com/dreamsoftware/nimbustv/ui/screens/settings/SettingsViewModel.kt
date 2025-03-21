package com.dreamsoftware.nimbustv.ui.screens.settings

import androidx.annotation.StringRes
import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.fudge.utils.FudgeTvEventBus
import com.dreamsoftware.nimbustv.AppEvent
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.di.SettingsScreenErrorMapper
import com.dreamsoftware.nimbustv.domain.model.EpgViewModeEnum
import com.dreamsoftware.nimbustv.domain.model.UserPreferenceBO
import com.dreamsoftware.nimbustv.domain.usecase.GetUserPreferencesUseCase
import com.dreamsoftware.nimbustv.domain.usecase.SaveUserPreferencesUseCase
import com.dreamsoftware.nimbustv.domain.usecase.SignOffUseCase
import com.dreamsoftware.nimbustv.utils.enumOfOrDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val signOffUseCase: SignOffUseCase,
    private val appEventBus: FudgeTvEventBus,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val saveUserPreferencesUseCase: SaveUserPreferencesUseCase,
    @SettingsScreenErrorMapper private val errorMapper: IFudgeTvErrorMapper,
) : FudgeTvViewModel<SettingsUiState, SettingsSideEffects>(), SettingsScreenActionListener {

    fun fetchData() {
        executeUseCase(
            useCase = getUserPreferencesUseCase,
            showLoadingState = false,
            onSuccess = ::onFetchUserPreferencesCompleted,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    override fun onGetDefaultState(): SettingsUiState = SettingsUiState(
        settingList = onBuildSettingsList()
    )

    override fun onSettingValueChanged(value: String) {
        uiState.value.settingSelected?.let { itemSelected ->
            updateState {
                it.copy(
                    settingList = it.settingList
                        .map { item ->
                            when {
                                item == itemSelected && item is ISettingItemVO.ISettingValueItemVO.SettingMultipleValuesVO -> item.copy(value = value)
                                item == itemSelected && item is ISettingItemVO.ISettingValueItemVO.SettingSingleValueVO -> item.copy(value = value)
                                else -> item
                            }
                        },
                    settingSelected = null
                )
            }
            onSaveUserPreferences()
        }
    }

    override fun onSettingItemSelected(setting: ISettingItemVO) {
        if(setting is ISettingItemVO.ISettingValueItemVO) {
            updateState { it.copy(settingSelected = setting) }
        } else if(setting is ISettingItemVO.SettingActionVO) {
            when(setting.type) {
                SettingActionTypeEnum.SIGN_OFF ->  updateState { it.copy(showSignOffDialog = true) }
                SettingActionTypeEnum.SUBSCRIPTIONS -> onOpenSubscriptions()
                SettingActionTypeEnum.OPEN_SYSTEM_SETTINGS -> onOpenSystemSettings()
            }
        }
    }

    override fun onSignOffConfirmed() {
        updateState { it.copy(showSignOffDialog = false) }
        onSignOff()
    }

    override fun onSignOffCancelled() {
        updateState { it.copy(showSignOffDialog = false) }
    }

    private fun onSignOff() {
        executeUseCase(
            useCase = signOffUseCase,
            onSuccess = { onSignOffCompleted() }
        )
    }

    private fun onSignOffCompleted() {
        appEventBus.send(AppEvent.SignOff)
    }

    private fun onOpenSubscriptions() {
        launchSideEffect(SettingsSideEffects.OpenSubscriptions)
    }

    private fun onOpenSystemSettings() {
        launchSideEffect(SettingsSideEffects.OpenSystemSettings)
    }

    private fun onFetchUserPreferencesCompleted(userPreferences: UserPreferenceBO) {
        updateState { it.copy(settingList = onBuildSettingsList(userPreferences)) }
    }

    private fun onSaveUserPreferences() {
        uiState.value.settingList.filterIsInstance<ISettingItemVO.ISettingValueItemVO.SettingMultipleValuesVO>()
            .let { settings ->
                executeUseCaseWithParams(
                    useCase = saveUserPreferencesUseCase,
                    showLoadingState = false,
                    params = SaveUserPreferencesUseCase.Params(
                        enableSearch = settings.find { it.type == SettingTypeEnum.ENABLE_SEARCH }?.value?.let { value ->
                            enumOfOrDefault({ it.value == value}, SettingsEnableSearchEnum.SEARCH_ENABLED)
                        } == SettingsEnableSearchEnum.SEARCH_ENABLED,
                        epgViewMode = settings.find { it.type == SettingTypeEnum.EPG_VIEW_MODE }?.value.orEmpty(),
                    ),
                    onSuccess = { onUserPreferencesUpdated() },
                    onMapExceptionToState = ::onMapExceptionToState
                )
            }
    }

    private fun onUserPreferencesUpdated() {
        appEventBus.send(AppEvent.UserPreferencesUpdated)
    }

    private fun onBuildSettingsList(userPreferences: UserPreferenceBO? = null) = listOf(
        ISettingItemVO.SettingHeaderVO(titleRes = R.string.app_settings),
        ISettingItemVO.ISettingValueItemVO.SettingMultipleValuesVO(
            titleRes = R.string.settings_enable_search_preference_title,
            value = if (userPreferences?.enableSearch == true) {
                SettingsEnableSearchEnum.SEARCH_ENABLED
            } else {
                SettingsEnableSearchEnum.SEARCH_DISABLED
            }.value,
            type = SettingTypeEnum.ENABLE_SEARCH,
            possibleValues = SettingsEnableSearchEnum.entries.map { it.value }
        ),
        ISettingItemVO.ISettingValueItemVO.SettingMultipleValuesVO(
            titleRes = R.string.settings_epg_view_mode_preference_title,
            value = userPreferences?.epgViewMode?.value ?: EpgViewModeEnum.NOW_AND_SCHEDULE.value,
            type = SettingTypeEnum.EPG_VIEW_MODE,
            possibleValues = EpgViewModeEnum.entries.map { it.value }
        ),
        ISettingItemVO.SettingActionVO(
            titleRes = R.string.settings_open_system_settings_title,
            type = SettingActionTypeEnum.OPEN_SYSTEM_SETTINGS
        ),
        ISettingItemVO.SettingHeaderVO(titleRes = R.string.about_settings),
        ISettingItemVO.ISettingValueItemVO.SettingSingleValueVO(
            titleRes = R.string.settings_about_app_title,
            valueRes = R.string.settings_about_app_content
        ),
        ISettingItemVO.ISettingValueItemVO.SettingSingleValueVO(
            titleRes = R.string.settings_terms_and_conditions_title,
            valueRes = R.string.settings_terms_and_conditions_content
        ),
        ISettingItemVO.ISettingValueItemVO.SettingSingleValueVO(
            titleRes = R.string.settings_help_title,
            valueRes = R.string.settings_help_content
        ),
        ISettingItemVO.ISettingValueItemVO.SettingSingleValueVO(
            titleRes = R.string.settings_about_me_title,
            valueRes = R.string.settings_about_me_content
        ),
        ISettingItemVO.SettingActionVO(
            titleRes = R.string.settings_close_session_title,
            type = SettingActionTypeEnum.SIGN_OFF
        )
    )

    private fun onMapExceptionToState(ex: Exception, uiState: SettingsUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )
}

data class SettingsUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val showSignOffDialog: Boolean = false,
    val settingList: List<ISettingItemVO> = emptyList(),
    val settingSelected: ISettingItemVO.ISettingValueItemVO? = null,
) : UiState<SettingsUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): SettingsUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface ISettingItemVO {
    val titleRes: Int

    sealed interface ISettingValueItemVO: ISettingItemVO {

        data class SettingSingleValueVO(
            @StringRes override val titleRes: Int,
            val value: String? = null,
            @StringRes val valueRes: Int? = null
        ) : ISettingValueItemVO

        data class SettingMultipleValuesVO(
            @StringRes override val titleRes: Int,
            val type: SettingTypeEnum,
            val value: String,
            val possibleValues: List<String> = emptyList(),
        ) : ISettingValueItemVO
    }

    data class SettingActionVO(
        @StringRes override val titleRes: Int,
        val type: SettingActionTypeEnum,
    ) : ISettingItemVO

    data class SettingHeaderVO(
        @StringRes override val titleRes: Int,
    ) : ISettingItemVO
}

enum class SettingTypeEnum {
    ENABLE_SEARCH, EPG_VIEW_MODE
}

enum class SettingActionTypeEnum {
    SIGN_OFF, SUBSCRIPTIONS, OPEN_SYSTEM_SETTINGS
}

enum class SettingsEnableSearchEnum(val value: String) {
    SEARCH_ENABLED("Enable"),
    SEARCH_DISABLED("Disable")
}

sealed interface SettingsSideEffects : SideEffect {
    data object OpenSubscriptions: SettingsSideEffects
    data object OpenSystemSettings: SettingsSideEffects
}