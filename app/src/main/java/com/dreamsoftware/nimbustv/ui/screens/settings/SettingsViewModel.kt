package com.dreamsoftware.nimbustv.ui.screens.settings

import androidx.annotation.StringRes
import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.fudge.utils.FudgeTvEventBus
import com.dreamsoftware.nimbustv.AppEvent
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.usecase.SignOffUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val signOffUseCase: SignOffUseCase,
    private val appEventBus: FudgeTvEventBus
) : FudgeTvViewModel<SettingsUiState, SettingsSideEffects>(), SettingsScreenActionListener {

    fun fetchData() {

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


    private fun onSaveUserPreferences() {
        uiState.value.settingList.filterIsInstance<ISettingItemVO.ISettingValueItemVO.SettingMultipleValuesVO>()
            .let { settings ->

            }
    }

    private fun onBuildSettingsList() = listOf(
        ISettingItemVO.SettingHeaderVO(titleRes = R.string.app_settings),
        ISettingItemVO.SettingHeaderVO(titleRes = R.string.about_settings),
        ISettingItemVO.ISettingValueItemVO.SettingSingleValueVO(
            titleRes = R.string.settings_about_app_title,
            valueRes = R.string.settings_about_app_content
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
    UNITS, APP_LANGUAGE, VIDEO_QUALITY
}

enum class SettingActionTypeEnum {
    SIGN_OFF, SUBSCRIPTIONS
}

sealed interface SettingsSideEffects : SideEffect {
    data object OpenSubscriptions: SettingsSideEffects
}