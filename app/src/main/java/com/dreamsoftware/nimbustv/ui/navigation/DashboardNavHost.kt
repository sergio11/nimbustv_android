package com.dreamsoftware.nimbustv.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dreamsoftware.nimbustv.domain.model.StreamTypeEnum
import com.dreamsoftware.nimbustv.ui.player.audio.AudioPlayerScreen
import com.dreamsoftware.nimbustv.ui.player.video.VideoPlayerScreen
import com.dreamsoftware.nimbustv.ui.screens.epg.EpgScreen
import com.dreamsoftware.nimbustv.ui.screens.favorites.FavoritesScreen
import com.dreamsoftware.nimbustv.ui.screens.home.HomeScreen
import com.dreamsoftware.nimbustv.ui.screens.playlistdetail.PlaylistDetailScreen
import com.dreamsoftware.nimbustv.ui.screens.playlists.PlaylistsScreen
import com.dreamsoftware.nimbustv.ui.screens.settings.SettingsScreen

@Composable
fun DashboardNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {
            with(navController) {
                HomeScreen(
                    onPlayChannel = { channelId, type ->
                        navigate(
                            if (type == StreamTypeEnum.ONLY_AUDIO) {
                                Screen.AudioPlayer.buildRoute(channelId)
                            } else {
                                Screen.VideoPlayer.buildRoute(channelId)
                            }
                        )
                    },
                    onManagePlaylist = {
                        navigate(Screen.Playlists.route)
                    }
                )
            }
        }

        composable(Screen.Playlists.route) {
            with(navController) {
                PlaylistsScreen(
                    onOpenPlaylistDetail = { playlistId ->
                        navigate(Screen.PlaylistDetail.buildRoute(playlistId))
                    },
                    onBackPressed = {
                        popBackStack()
                    }
                )
            }
        }

        composable(Screen.PlaylistDetail.route) { navBackStackEntry ->
            navBackStackEntry.arguments?.let(Screen.PlaylistDetail::parseArgs)?.let { args ->
                with(navController) {
                    PlaylistDetailScreen(
                        args = args,
                        onPlayChannel = { channelId, type ->
                            navigate(
                                if (type == StreamTypeEnum.ONLY_AUDIO) {
                                    Screen.AudioPlayer.buildRoute(channelId)
                                } else {
                                    Screen.VideoPlayer.buildRoute(channelId)
                                }
                            )
                        },
                        onBackPressed = {
                            popBackStack()
                        })
                }
            }
        }

        composable(Screen.Favorite.route) {
            with(navController) {
                FavoritesScreen(
                    onPlayChannel = { channelId, type ->
                        navigate(
                            if (type == StreamTypeEnum.ONLY_AUDIO) {
                                Screen.AudioPlayer.buildRoute(channelId)
                            } else {
                                Screen.VideoPlayer.buildRoute(channelId)
                            }
                        )
                    },
                    onBackPressed = {
                        popBackStack()
                    }
                )
            }
        }

        composable(Screen.Epg.route) {
            with(navController) {
                EpgScreen(
                    onBackPressed = {
                        popBackStack()
                    }
                )
            }
        }

        composable(Screen.AudioPlayer.route) { navBackStackEntry ->
            navBackStackEntry.arguments?.let(Screen.AudioPlayer::parseArgs)?.let { args ->
                with(navController) {
                    AudioPlayerScreen(args = args) {
                        popBackStack()
                    }
                }
            }
        }

        composable(Screen.VideoPlayer.route) { navBackStackEntry ->
            navBackStackEntry.arguments?.let(Screen.VideoPlayer::parseArgs)?.let { args ->
                with(navController) {
                    VideoPlayerScreen(args = args) {
                        popBackStack()
                    }
                }
            }
        }

        composable(Screen.Settings.route) {
            with(navController) {
                SettingsScreen(
                    onGoToSubscriptions = {
                        navigate(Screen.Subscription.route)
                    },
                    onBackPressed = {
                        popBackStack()
                    }
                )
            }
        }
    }
}