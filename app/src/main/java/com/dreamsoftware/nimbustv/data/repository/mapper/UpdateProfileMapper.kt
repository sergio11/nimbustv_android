package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.ProfileEntity
import com.dreamsoftware.nimbustv.domain.model.UpdatedProfileRequestBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper

internal class UpdateProfileMapper: IOneSideMapper<UpdateProfileData, ProfileEntity> {

    override fun mapInToOut(input: UpdateProfileData): ProfileEntity = with(input) {
        ProfileEntity(
            id = currentProfile.id,
            alias = updatedProfileRequest.alias ?: currentProfile.alias,
            avatarType = updatedProfileRequest.avatarType?.name ?: currentProfile.avatarType,
            securedPin = updatedProfileRequest.pin?.toString() ?: currentProfile.securedPin
        )
    }

    override fun mapInListToOutList(input: Iterable<UpdateProfileData>): Iterable<ProfileEntity> =
        input.map(::mapInToOut)
}

data class UpdateProfileData(
    val currentProfile: ProfileEntity,
    val updatedProfileRequest: UpdatedProfileRequestBO
)