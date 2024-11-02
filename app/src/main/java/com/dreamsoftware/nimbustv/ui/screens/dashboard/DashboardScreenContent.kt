package com.dreamsoftware.nimbustv.ui.screens.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.dreamsoftware.fudge.component.FudgeTvNavigationDrawer
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.ui.navigation.DashboardNavHost
import com.dreamsoftware.nimbustv.ui.navigation.Screen
import com.dreamsoftware.nimbustv.ui.theme.Dimens.DRAWER_MAIN_LOGO_SEARCH_DISABLED
import com.dreamsoftware.nimbustv.ui.theme.Dimens.DRAWER_MAIN_LOGO_SEARCH_ENABLED

@Composable
internal fun DashboardScreenContent(
    uiState: DashboardUiState,
    actionListener: DashboardActionListener,
    navController: NavHostController,
    currentDestination: NavDestination?,
) {
    with(uiState) {
        FudgeTvNavigationDrawer(
            modifier = Modifier,
            mainLogoInverseRes = R.drawable.main_logo_inverse,
            logoSize = if(searchEnabled) {
                DRAWER_MAIN_LOGO_SEARCH_ENABLED
            } else {
                DRAWER_MAIN_LOGO_SEARCH_DISABLED
            },
            hiddenDrawerRoutes = listOf(Screen.VideoPlayer.route, Screen.AudioPlayer.route),
            onItemClicked = actionListener::onMenuItemSelected,
            items = items,
            currentDestination = currentDestination,
        ) {
            DashboardNavHost(navController)
        }
    }
}
