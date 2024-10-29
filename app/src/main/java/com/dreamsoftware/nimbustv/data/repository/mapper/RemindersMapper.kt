package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.ReminderEntity
import com.dreamsoftware.nimbustv.domain.model.ReminderBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper

internal class RemindersMapper: IOneSideMapper<ReminderEntity, ReminderBO> {

    override fun mapInToOut(input: ReminderEntity): ReminderBO = with(input) {
        ReminderBO(
            id = id,
            scheduleId = scheduleId
        )
    }

    override fun mapInListToOutList(input: Iterable<ReminderEntity>): Iterable<ReminderBO> =
        input.map(::mapInToOut)
}