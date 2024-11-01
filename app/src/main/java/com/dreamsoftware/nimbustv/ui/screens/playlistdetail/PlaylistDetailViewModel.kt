package com.dreamsoftware.nimbustv.ui.screens.playlistdetail

import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.di.PlaylistDetailScreenErrorMapper
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.model.StreamTypeEnum
import com.dreamsoftware.nimbustv.domain.usecase.DeleteChannelByIdUseCase
import com.dreamsoftware.nimbustv.domain.usecase.DeletePlaylistUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetChannelsByPlaylistUseCase
import com.dreamsoftware.nimbustv.ui.utils.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistDetailViewModel @Inject constructor(
    private val getChannelsByPlaylistUseCase: GetChannelsByPlaylistUseCase,
    private val deletePlaylistUseCase: DeletePlaylistUseCase,
    private val deleteChannelByIdUseCase: DeleteChannelByIdUseCase,
    @PlaylistDetailScreenErrorMapper private val errorMapper: IFudgeTvErrorMapper,
) : FudgeTvViewModel<PlaylistDetailUiState, PlaylistDetailSideEffects>(), PlaylistDetailScreenActionListener {

    override fun onGetDefaultState(): PlaylistDetailUiState = PlaylistDetailUiState()

    fun fetchData(id: String) {
        updateState { it.copy(playlistId = id) }
        executeUseCaseWithParams(
            useCase = getChannelsByPlaylistUseCase,
            params = GetChannelsByPlaylistUseCase.Params(
                playlistId = id
            ),
            onSuccess = ::onGetChannelsByPlaylistCompleted,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    override fun onDeletePlaylistConfirmed() {
        updateState { it.copy(showDeletePlaylistDialog = false) }
        executeUseCaseWithParams(
            useCase = deletePlaylistUseCase,
            params = DeletePlaylistUseCase.Params(
                playlistId = uiState.value.playlistId
            ),
            onSuccess = { onPlayListRemoved() }
        )
    }

    override fun onDeletePlaylistCancelled() {
        updateState { it.copy(showDeletePlaylistDialog = false) }
    }

    override fun onPlayChannel(channelBO: ChannelBO) {
        launchSideEffect(PlaylistDetailSideEffects.PlayChannelSideEffect(
            channelId = channelBO.id,
            streamTypeEnum = channelBO.streamTypeEnum
        ))
    }

    override fun onRemoveFromPlaylist(channelBO: ChannelBO) {
        executeUseCaseWithParams(
            useCase = deleteChannelByIdUseCase,
            params = DeleteChannelByIdUseCase.Params(
                channelId = channelBO.id
            ),
            onSuccess = { onChannelRemoved(channelBO) }
        )
    }

    override fun onCloseChannelDetail() {
        updateState { it.copy(channelSelected = null) }
    }

    override fun onOpenChannelDetail(channel: ChannelBO) {
        updateState { it.copy(channelSelected = channel) }
    }

    override fun onDeletePlaylistClicked() {
        updateState { it.copy(showDeletePlaylistDialog = true) }
    }

    private fun onPlayListRemoved() {
        launchSideEffect(PlaylistDetailSideEffects.PlaylistRemovedSideEffect)
    }

    private fun onGetChannelsByPlaylistCompleted(data: List<ChannelBO>) {
        updateState { it.copy(channels = data) }
    }

    private fun onChannelRemoved(channelBO: ChannelBO) {
        updateState {
            it.copy(
                channelSelected = null,
                channels = it.channels.filterNot { channel -> channel.id == channelBO.id }
            )
        }
    }

    private fun onMapExceptionToState(ex: Exception, uiState: PlaylistDetailUiState) =
        uiState.copy(
            channelSelected = null,
            showDeletePlaylistDialog = false,
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )
}

data class PlaylistDetailUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val playlistId: String = String.EMPTY,
    val channels: List<ChannelBO> = emptyList(),
    val showDeletePlaylistDialog: Boolean = false,
    val channelSelected: ChannelBO? = null
): UiState<PlaylistDetailUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): PlaylistDetailUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface PlaylistDetailSideEffects: SideEffect {
    data object PlaylistRemovedSideEffect: PlaylistDetailSideEffects
    data class PlayChannelSideEffect(val channelId: String, val streamTypeEnum: StreamTypeEnum): PlaylistDetailSideEffects
}