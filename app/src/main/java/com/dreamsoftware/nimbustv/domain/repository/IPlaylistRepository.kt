package com.dreamsoftware.nimbustv.domain.repository

import com.dreamsoftware.nimbustv.domain.exception.DeletePlaylistsException
import com.dreamsoftware.nimbustv.domain.exception.GetPlaylistsByProfileException
import com.dreamsoftware.nimbustv.domain.exception.InsertPlaylistException
import com.dreamsoftware.nimbustv.domain.exception.UpdatePlaylistException
import com.dreamsoftware.nimbustv.domain.model.CreatePlayListBO
import com.dreamsoftware.nimbustv.domain.model.PlayListBO
import com.dreamsoftware.nimbustv.domain.model.UpdatePlayListBO

interface IPlaylistRepository {

    @Throws(InsertPlaylistException::class)
    suspend fun insert(data: CreatePlayListBO): PlayListBO

    @Throws(UpdatePlaylistException::class)
    suspend fun update(data: UpdatePlayListBO): PlayListBO

    @Throws(GetPlaylistsByProfileException::class)
    suspend fun findAllByProfileId(profileId: String): List<PlayListBO>

    @Throws(DeletePlaylistsException::class)
    suspend fun deleteById(id: String)
}