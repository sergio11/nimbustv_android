package com.dreamsoftware.nimbustv.data.repository.impl

import com.dreamsoftware.nimbustv.data.database.datasource.IReminderLocalDataSource
import com.dreamsoftware.nimbustv.data.database.entity.ReminderEntity
import com.dreamsoftware.nimbustv.data.database.exception.DatabaseException
import com.dreamsoftware.nimbustv.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.nimbustv.domain.exception.CreateReminderException
import com.dreamsoftware.nimbustv.domain.exception.DeleteReminderByIdException
import com.dreamsoftware.nimbustv.domain.exception.FetchReminderByIdException
import com.dreamsoftware.nimbustv.domain.exception.FetchReminderByScheduleIdException
import com.dreamsoftware.nimbustv.domain.exception.FetchRemindersByProfileException
import com.dreamsoftware.nimbustv.domain.exception.VerifyReminderByIdException
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

    @Throws(FetchReminderByIdException::class)
    override suspend fun findById(id: String): ReminderBO = safeExecute {
        try {
            reminderLocalDataSource
                .findById(id)
                .let(remindersMapper::mapInToOut)
        } catch (ex: DatabaseException) {
            throw FetchReminderByIdException("An error occurred when fetching reminder by id", ex)
        }
    }

    @Throws(FetchReminderByScheduleIdException::class)
    override suspend fun findByScheduleId(scheduleId: String): ReminderBO = safeExecute {
        try {
            reminderLocalDataSource
                .findByScheduleId(scheduleId)
                .let(remindersMapper::mapInToOut)
        } catch (ex: DatabaseException) {
            throw FetchReminderByScheduleIdException("An error occurred when fetching reminder by schedule id", ex)
        }
    }

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

    @Throws(DeleteReminderByIdException::class)
    override suspend fun deleteById(id: String): Unit = safeExecute {
        try {
            reminderLocalDataSource.delete(id)
        } catch (ex: DatabaseException) {
            throw DeleteReminderByIdException("An error occurred when trying to delete reminders", ex)
        }
    }

    @Throws(VerifyReminderByIdException::class)
    override suspend fun hasReminder(scheduleId: String): Boolean = safeExecute {
        try {
            reminderLocalDataSource.hasReminder(scheduleId)
        } catch (ex: DatabaseException) {
            throw VerifyReminderByIdException("An error occurred when trying to verify if schedule has a reminder set", ex)
        }
    }
}