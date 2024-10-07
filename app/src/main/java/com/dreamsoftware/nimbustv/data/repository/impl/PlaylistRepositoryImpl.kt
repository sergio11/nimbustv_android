package com.dreamsoftware.nimbustv.data.repository.impl

import com.dreamsoftware.nimbustv.data.database.datasource.IPlayListLocalDataSource
import com.dreamsoftware.nimbustv.data.database.entity.PlayListEntity
import com.dreamsoftware.nimbustv.data.database.exception.DatabaseException
import com.dreamsoftware.nimbustv.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.nimbustv.data.repository.mapper.UpdatePlaylistData
import com.dreamsoftware.nimbustv.domain.exception.InsertPlaylistException
import com.dreamsoftware.nimbustv.domain.exception.UpdatePlaylistException
import com.dreamsoftware.nimbustv.domain.model.CreatePlayListBO
import com.dreamsoftware.nimbustv.domain.model.PlayListBO
import com.dreamsoftware.nimbustv.domain.model.UpdatePlayListBO
import com.dreamsoftware.nimbustv.domain.repository.IPlaylistRepository
import com.dreamsoftware.nimbustv.utils.IOneSideMapper
import kotlinx.coroutines.CoroutineDispatcher

internal class PlaylistRepositoryImpl(
    private val playListLocalDataSource: IPlayListLocalDataSource,
    private val createPlaylistMapper: IOneSideMapper<CreatePlayListBO, PlayListEntity>,
    private val updatePlaylistMapper: IOneSideMapper<UpdatePlaylistData, PlayListEntity>,
    private val playlistMapper: IOneSideMapper<PlayListEntity, PlayListBO>,
    dispatcher: CoroutineDispatcher
) : SupportRepositoryImpl(dispatcher), IPlaylistRepository {

    @Throws(InsertPlaylistException::class)
    override suspend fun insert(data: CreatePlayListBO): PlayListBO = safeExecute {
        try {
            playListLocalDataSource
                .insert(createPlaylistMapper.mapInToOut(data))
                .let(playlistMapper::mapInToOut)
        } catch (ex: DatabaseException) {
            throw InsertPlaylistException(
                "An error occurred when trying to insert the playlist",
                ex
            )
        }
    }

    @Throws(UpdatePlaylistException::class)
    override suspend fun update(data: UpdatePlayListBO): PlayListBO = safeExecute {
        try {
            with(playListLocalDataSource) {
                val currentPlaylist = findById(id = data.id)
                update(
                    updatePlaylistMapper.mapInToOut(
                        UpdatePlaylistData(
                            playlist = currentPlaylist,
                            newAlias = data.alias
                        )
                    )
                ).let(playlistMapper::mapInToOut)
            }

        } catch (ex: DatabaseException) {
            throw InsertPlaylistException(
                "An error occurred when trying to update the playlist",
                ex
            )
        }
    }
}