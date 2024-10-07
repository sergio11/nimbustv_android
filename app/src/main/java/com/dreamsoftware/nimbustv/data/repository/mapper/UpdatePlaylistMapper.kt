package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.PlayListEntity
import com.dreamsoftware.nimbustv.utils.IOneSideMapper

internal class UpdatePlaylistMapper: IOneSideMapper<UpdatePlaylistData, PlayListEntity> {

    override fun mapInToOut(input: UpdatePlaylistData): PlayListEntity = with(input) {
        playlist.copy(alias = newAlias)
    }

    override fun mapInListToOutList(input: Iterable<UpdatePlaylistData>): Iterable<PlayListEntity> =
        input.map(::mapInToOut)
}

data class UpdatePlaylistData(
    val playlist: PlayListEntity,
    val newAlias: String
)