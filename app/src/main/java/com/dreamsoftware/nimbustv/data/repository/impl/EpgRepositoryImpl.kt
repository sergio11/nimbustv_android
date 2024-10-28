package com.dreamsoftware.nimbustv.data.repository.impl

import com.dreamsoftware.nimbustv.data.database.datasource.IChannelEpgLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IEpgLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IChannelScheduleEpgLocalDataSource
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEpgEntity
import com.dreamsoftware.nimbustv.data.database.entity.EpgEntity
import com.dreamsoftware.nimbustv.data.database.entity.ChannelScheduleEntity
import com.dreamsoftware.nimbustv.data.database.exception.DatabaseException
import com.dreamsoftware.nimbustv.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.nimbustv.data.repository.mapper.EpgDataInput
import com.dreamsoftware.nimbustv.domain.exception.DeleteEpgDataException
import com.dreamsoftware.nimbustv.domain.exception.GetEpgChannelsDataException
import com.dreamsoftware.nimbustv.domain.exception.GetEpgDataException
import com.dreamsoftware.nimbustv.domain.exception.SaveEpgDataException
import com.dreamsoftware.nimbustv.domain.model.CreateEpgBO
import com.dreamsoftware.nimbustv.domain.model.CreateEpgChannelBO
import com.dreamsoftware.nimbustv.domain.model.CreateEpgScheduleBO
import com.dreamsoftware.nimbustv.domain.model.EpgBO
import com.dreamsoftware.nimbustv.domain.model.EpgChannelBO
import com.dreamsoftware.nimbustv.domain.repository.IEpgRepository
import com.dreamsoftware.nimbustv.utils.IOneSideMapper
import kotlinx.coroutines.CoroutineDispatcher

internal class EpgRepositoryImpl(
    private val programmeLocalDataSource: IChannelScheduleEpgLocalDataSource,
    private val channelLocalDataSource: IChannelEpgLocalDataSource,
    private val epgLocalDataSource: IEpgLocalDataSource,
    private val saveEpgDataMapper: IOneSideMapper<CreateEpgBO, EpgEntity>,
    private val saveEpgChannelDataMapper: IOneSideMapper<CreateEpgChannelBO, ChannelEpgEntity>,
    private val saveEpgProgrammeDataMapper: IOneSideMapper<CreateEpgScheduleBO, ChannelScheduleEntity>,
    private val epgDataMapper: IOneSideMapper<EpgEntity, EpgBO>,
    private val epgChannelsDataMapper: IOneSideMapper<EpgDataInput, List<EpgChannelBO>>,
    dispatcher: CoroutineDispatcher
) : SupportRepositoryImpl(dispatcher), IEpgRepository {

    @Throws(SaveEpgDataException::class)
    override suspend fun save(data: CreateEpgBO): EpgBO = safeExecute {
        try {
            val epgSaved = epgLocalDataSource.insert(saveEpgDataMapper.mapInToOut(data)).also {
                channelLocalDataSource.insert(saveEpgChannelDataMapper.mapInListToOutList(data.channelList).toList())
                programmeLocalDataSource.insert(saveEpgProgrammeDataMapper.mapInListToOutList(data.channelList.flatMap { it.programmeList }).toList())
            }
            epgDataMapper.mapInToOut(epgSaved)
        } catch (ex: DatabaseException) {
            ex.printStackTrace()
            throw SaveEpgDataException(
                "An error occurred when trying to save EPG data",
                ex
            )
        }
    }

    @Throws(GetEpgDataException::class)
    override suspend fun findAllByProfileId(profileId: String): List<EpgBO> = safeExecute {
        try {
            epgLocalDataSource.findAllByProfileId(profileId)
                .let(epgDataMapper::mapInListToOutList)
                .toList()
        } catch (ex: DatabaseException) {
            ex.printStackTrace()
            throw GetEpgDataException(
                "An error occurred when trying to get EPG data",
                ex
            )
        }
    }

    @Throws(GetEpgChannelsDataException::class)
    override suspend fun findAllChannelsByEpgId(epgId: String): List<EpgChannelBO> = safeExecute {
        try {
            val channelsSaved = channelLocalDataSource.findAllByEpgId(epgId)
            val programmeListSaved = programmeLocalDataSource.findAllByEpgId(epgId)
            epgChannelsDataMapper.mapInToOut(EpgDataInput(channelsSaved, programmeListSaved))
        } catch (ex: DatabaseException) {
            ex.printStackTrace()
            throw GetEpgChannelsDataException(
                "An error occurred when trying to get EPG channels data",
                ex
            )
        }
    }

    @Throws(DeleteEpgDataException::class)
    override suspend fun deleteAllByProfileId(profileId: String) = safeExecute {
        try {
            epgLocalDataSource.deleteAllByProfileId(profileId)
        } catch (ex: DatabaseException) {
            ex.printStackTrace()
            throw DeleteEpgDataException(
                "An error occurred when trying to delete EPG data",
                ex
            )
        }
    }
}