package com.dreamsoftware.nimbustv.ui.navigation

import android.os.Bundle
import android.telecom.InCallService.VideoCall
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.dreamsoftware.nimbustv.ui.player.audio.AudioPlayerScreenArgs
import com.dreamsoftware.nimbustv.ui.player.video.VideoPlayerScreenArgs
import com.dreamsoftware.nimbustv.ui.screens.profiles.changesecurepin.ChangeSecurePinScreenArgs
import com.dreamsoftware.nimbustv.ui.screens.profiles.delete.DeleteProfileScreenArgs
import com.dreamsoftware.nimbustv.ui.screens.profiles.save.SaveProfileScreenArgs
import com.dreamsoftware.nimbustv.ui.screens.profiles.secure.SecurePinScreenArgs

sealed class Screen(
    val route: String,
    val name: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {

    data object Splash: Screen(route = "splash", name = "Splash")
    data object Onboarding: Screen(route = "onboarding", name = "Onboarding")
    data object Dashboard: Screen(route = "dashboard", name = "Dashboard")
    data object Subscription: Screen(route = "subscription", name = "Subscription")
    data object Profiles: Screen(route = "profiles", name = "Profiles")
    data object ProfileSelector: Screen(route = "profile_selector", name = "ProfileSelector")
    data object ProfilesManagement: Screen(route = "profile_management", name = "ProfilesManagement")
    data object AddProfile: Screen(route = "add_profile", name = "AddProfile")
    data object EditProfile: Screen(route = "edit_profile/{id}", name = "EditProfile", arguments = listOf(
        navArgument("id") {
            type = NavType.StringType
        }
    )) {
        fun buildRoute(id: String): String =
            route.replace(
                oldValue = "{id}",
                newValue = id
            )

        fun parseArgs(args: Bundle): SaveProfileScreenArgs? = with(args) {
            getString("id")?.let { id ->
                SaveProfileScreenArgs(
                    profileId = id,
                )
            }
        }
    }
    data object ProfileAdvance: Screen(route = "profile_advance/{id}", name = "ProfileAdvance", arguments = listOf(
        navArgument("id") {
            type = NavType.StringType
        }
    )) {
        fun buildRoute(id: String): String =
            route.replace(
                oldValue = "{id}",
                newValue = id
            )

        fun parseArgs(args: Bundle): ChangeSecurePinScreenArgs? = with(args) {
            getString("id")?.let { id ->
                ChangeSecurePinScreenArgs(
                    profileId = id,
                )
            }
        }
    }
    data object DeleteProfile: Screen(route = "delete_profile/{id}", name = "DeleteProfile", arguments = listOf(
        navArgument("id") {
            type = NavType.StringType
        }
    )) {
        fun buildRoute(id: String): String =
            route.replace(
                oldValue = "{id}",
                newValue = id
            )

        fun parseArgs(args: Bundle): DeleteProfileScreenArgs? = with(args) {
            getString("id")?.let { id ->
                DeleteProfileScreenArgs(
                    profileId = id,
                )
            }
        }
    }
    data object UnlockProfile: Screen(route = "unlock_profile/{id}", name = "UnlockProfile", arguments = listOf(
        navArgument("id") {
            type = NavType.StringType
        }
    )) {
        fun buildRoute(id: String): String =
            route.replace(
                oldValue = "{id}",
                newValue = id
            )

        fun parseArgs(args: Bundle): SecurePinScreenArgs? = with(args) {
            getString("id")?.let { id ->
                SecurePinScreenArgs(id)
            }
        }
    }
    data object VideoPlayer: Screen(route = "video_player/{id}", name = "VideoPlayer", arguments = listOf(
        navArgument("id") {
            type = NavType.StringType
        }
    )) {
        fun buildRoute(id: String): String =
            route.replace(
                oldValue = "{id}",
                newValue = id
            )

        fun parseArgs(args: Bundle): VideoPlayerScreenArgs? = with(args) {
            getString("id")?.let { id ->
                VideoPlayerScreenArgs(
                    id = id
                )
            }
        }
    }
    data object AudioPlayer: Screen(route = "audio_player/{id}", name = "AudioPlayer", arguments = listOf(
        navArgument("id") {
            type = NavType.StringType
        }
    )) {
        fun buildRoute(id: String): String =
            route.replace(
                oldValue = "{id}",
                newValue = id
            )

        fun parseArgs(args: Bundle): AudioPlayerScreenArgs? = with(args) {
            getString("id")?.let { id ->
                AudioPlayerScreenArgs(
                    id = id
                )
            }
        }
    }

    data object Home: Screen(route = "home", name = "Home")
    data object Favorite: Screen(route = "favorite", name = "Favorite")
    data object Settings: Screen(route = "settings", name = "Settings")
}