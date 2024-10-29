package com.dreamsoftware.nimbustv.data.repository.impl

import com.dreamsoftware.nimbustv.data.database.datasource.IReminderLocalDataSource
import com.dreamsoftware.nimbustv.data.database.entity.ReminderEntity
import com.dreamsoftware.nimbustv.data.database.exception.DatabaseException
import com.dreamsoftware.nimbustv.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.nimbustv.domain.exception.CreateReminderException
import com.dreamsoftware.nimbustv.domain.exception.FetchRemindersByProfileException
import com.dreamsoftware.nimbustv.domain.model.CreateReminderBO
import com.dreamsoftware.nimbustv.domain.model.ReminderBO
import com.dreamsoftware.nimbustv.domain.repository.IRemindersRepository
import com.dreamsoftware.nimbustv.utils.IOneSideMapper
import kotlinx.coroutines.CoroutineDispatcher

internal class RemindersRepositoryImpl(
    private val reminderLocalDataSource: IReminderLocalDataSource,
    private val remindersMapper: IOneSideMapper<ReminderEntity, ReminderBO>,
    private val createReminderMapper: IOneSideMapper<CreateReminderBO, ReminderEntity>,
    dispatcher: CoroutineDispatcher
): SupportRepositoryImpl(dispatcher), IRemindersRepository {

    @Throws(FetchRemindersByProfileException::class)
    override suspend fun findAllByProfileId(profileId: String): List<ReminderBO> = safeExecute {
        try {
            reminderLocalDataSource
                .findAllByProfileId(profileId)
                .let(remindersMapper::mapInListToOutList)
                .toList()
        } catch (ex: DatabaseException) {
            throw FetchRemindersByProfileException("An error occurred when fetching reminders", ex)
        }
    }

    @Throws(CreateReminderException::class)
    override suspend fun create(data: CreateReminderBO): ReminderBO = safeExecute {
        try {
            reminderLocalDataSource
                .insert(createReminderMapper.mapInToOut(data))
                .let(remindersMapper::mapInToOut)
        } catch (ex: DatabaseException) {
            throw FetchRemindersByProfileException("An error occurred when trying to save a new reminder", ex)
        }
    }
}