package com.dreamsoftware.nimbustv.data.repository.impl

import com.dreamsoftware.nimbustv.data.database.datasource.IChannelEpgLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IProgrammeLocalDataSource
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEpgEntity
import com.dreamsoftware.nimbustv.data.database.entity.ProgrammeEntity
import com.dreamsoftware.nimbustv.data.database.exception.DatabaseException
import com.dreamsoftware.nimbustv.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.nimbustv.data.repository.mapper.EpgDataInput
import com.dreamsoftware.nimbustv.domain.exception.DeleteEpgDataException
import com.dreamsoftware.nimbustv.domain.exception.GetEpgDataException
import com.dreamsoftware.nimbustv.domain.exception.InsertPlaylistException
import com.dreamsoftware.nimbustv.domain.exception.SaveEpgDataException
import com.dreamsoftware.nimbustv.domain.model.ChannelEpgDataBO
import com.dreamsoftware.nimbustv.domain.repository.IEpgRepository
import com.dreamsoftware.nimbustv.utils.IOneSideMapper
import kotlinx.coroutines.CoroutineDispatcher

internal class EpgRepositoryImpl(
    private val programmeLocalDataSource: IProgrammeLocalDataSource,
    private val channelLocalDataSource: IChannelEpgLocalDataSource,
    private val saveChannelEpgDataMapper: IOneSideMapper<ChannelEpgDataBO, ChannelEpgEntity>,
    private val saveProgrammeEpgDataMapper: IOneSideMapper<ChannelEpgDataBO, Iterable<ProgrammeEntity>>,
    private val epgDataMapper: IOneSideMapper<EpgDataInput, List<ChannelEpgDataBO>>,
    dispatcher: CoroutineDispatcher
) : SupportRepositoryImpl(dispatcher), IEpgRepository {

    @Throws(SaveEpgDataException::class)
    override suspend fun save(data: List<ChannelEpgDataBO>): List<ChannelEpgDataBO> = safeExecute {
        try {
            val channels = saveChannelEpgDataMapper.mapInListToOutList(data).toList()
            val programmeList = saveProgrammeEpgDataMapper.mapInListToOutList(data).flatten()
            val channelsSaved = channelLocalDataSource.insert(channels)
            val programmeListSaved = programmeLocalDataSource.insert(programmeList)
            epgDataMapper.mapInToOut(EpgDataInput(channelsSaved, programmeListSaved))
        } catch (ex: DatabaseException) {
            ex.printStackTrace()
            throw InsertPlaylistException(
                "An error occurred when trying to save EPG data",
                ex
            )
        }
    }

    @Throws(GetEpgDataException::class)
    override suspend fun findAllByProfileId(profileId: String): List<ChannelEpgDataBO> = safeExecute {
        try {
            val channelsSaved = channelLocalDataSource.findAll()
            val programmeListSaved = programmeLocalDataSource.findAll()
            epgDataMapper.mapInToOut(EpgDataInput(channelsSaved, programmeListSaved))
        } catch (ex: DatabaseException) {
            ex.printStackTrace()
            throw GetEpgDataException(
                "An error occurred when trying to get EPG data",
                ex
            )
        }
    }

    @Throws(DeleteEpgDataException::class)
    override suspend fun deleteAllByProfileId(profileId: String) = safeExecute {
        try {
            channelLocalDataSource.deleteAllByProfileId(profileId)
        } catch (ex: DatabaseException) {
            ex.printStackTrace()
            throw DeleteEpgDataException(
                "An error occurred when trying to delete EPG data",
                ex
            )
        }
    }
}