package com.dreamsoftware.nimbustv.ui.screens.dashboard

import androidx.lifecycle.viewModelScope
import com.dreamsoftware.fudge.component.FudgeTvNavigationDrawerItemModel
import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.fudge.utils.FudgeTvEventBus
import com.dreamsoftware.nimbustv.AppEvent
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.model.AvatarTypeEnum
import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.domain.model.UserPreferenceBO
import com.dreamsoftware.nimbustv.domain.usecase.GetProfileSelectedUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetUserPreferencesUseCase
import com.dreamsoftware.nimbustv.ui.navigation.Screen
import com.dreamsoftware.nimbustv.ui.utils.toDrawableResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getProfileSelectedUseCase: GetProfileSelectedUseCase,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val appEventBus: FudgeTvEventBus
) : FudgeTvViewModel<DashboardUiState, DashboardSideEffects>(), DashboardActionListener {

    init {
        observeEvents()
    }

    fun fetchData() {
        viewModelScope.launch {
            val profile = fetchCurrentSelectedProfile()
            val userPreferences = fetchUserPreferences()
            updateState {
                it.copy(
                    searchEnabled = userPreferences?.enableSearch ?: false,
                    items = onBuildNavigationDrawerMenuItems(profile, userPreferences)
                )
            }
        }
    }

    override fun onGetDefaultState(): DashboardUiState = DashboardUiState(
        items = onBuildNavigationDrawerMenuItems()
    )

    override fun onMenuItemSelected(menuItem: FudgeTvNavigationDrawerItemModel) {
        launchSideEffect(DashboardSideEffects.OpenScreen(menuItem.route))
    }

    private fun onBuildNavigationDrawerMenuItems(
        currentProfile: ProfileBO? = null,
        userPreferences: UserPreferenceBO? = null
    ) = buildList {
        add(
            FudgeTvNavigationDrawerItemModel(
                nameRes = if (currentProfile == null) {
                    R.string.dashboard_navigation_drawer_profile_item_name
                } else {
                    null
                },
                name = currentProfile?.alias,
                imageRes = (currentProfile?.avatarType ?: AvatarTypeEnum.BOY).toDrawableResource(),
                route = Screen.Profiles.route,
                isIcon = false
            )
        )
        add(
            FudgeTvNavigationDrawerItemModel(
                nameRes = R.string.dashboard_navigation_drawer_home_item_name,
                imageRes = R.drawable.home,
                route = Screen.Home.route
            )
        )
        add(
            FudgeTvNavigationDrawerItemModel(
                nameRes = R.string.dashboard_navigation_drawer_favorite_item_name,
                imageRes = R.drawable.favorite,
                route = Screen.Favorite.route
            )
        )
        if(userPreferences?.enableSearch == true) {
            add(
                FudgeTvNavigationDrawerItemModel(
                    nameRes = R.string.dashboard_navigation_drawer_search_item_name,
                    imageRes = R.drawable.ic_search,
                    route = Screen.Search.route
                )
            )
        }
        add(
            FudgeTvNavigationDrawerItemModel(
                nameRes = R.string.dashboard_navigation_drawer_epg_item_name,
                imageRes = R.drawable.ic_epg_icon,
                route = Screen.Epg.route
            )
        )
        add(
            FudgeTvNavigationDrawerItemModel(
                nameRes = R.string.dashboard_navigation_drawer_settings_item_name,
                imageRes = R.drawable.settings,
                route = Screen.Settings.route
            )
        )
    }

    private suspend fun fetchCurrentSelectedProfile(): ProfileBO? = runCatching {
        getProfileSelectedUseCase.onExecuted()
    }.getOrNull()

    private suspend fun fetchUserPreferences(): UserPreferenceBO? = runCatching {
        getUserPreferencesUseCase.onExecuted()
    }.getOrNull()

    private fun observeEvents() {
        viewModelScope.launch {
            appEventBus.events.collect { event ->
                if(event is AppEvent.UserPreferencesUpdated) {
                    fetchData()
                }
            }
        }
    }
}

data class DashboardUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val searchEnabled: Boolean = false,
    val items: List<FudgeTvNavigationDrawerItemModel> = emptyList(),
) : UiState<DashboardUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): DashboardUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface DashboardSideEffects : SideEffect {
    data class OpenScreen(val route: String) : DashboardSideEffects
}