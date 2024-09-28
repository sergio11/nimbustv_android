package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.ProfileEntity
import com.dreamsoftware.nimbustv.domain.model.UpdatedProfileRequestBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper

internal class UpdateProfileMapper: IOneSideMapper<UpdatedProfileRequestBO, ProfileEntity> {
    override fun mapInToOut(input: UpdatedProfileRequestBO): ProfileEntity {
        TODO("Not yet implemented")
    }

    override fun mapInListToOutList(input: Iterable<UpdatedProfileRequestBO>): Iterable<ProfileEntity> {
        TODO("Not yet implemented")
    }
}