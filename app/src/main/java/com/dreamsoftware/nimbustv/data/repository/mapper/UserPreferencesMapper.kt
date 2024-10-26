package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.preferences.dto.UserPreferencesDTO
import com.dreamsoftware.nimbustv.domain.model.EpgViewModeEnum
import com.dreamsoftware.nimbustv.domain.model.UserPreferenceBO
import com.dreamsoftware.nimbustv.utils.IMapper
import com.dreamsoftware.nimbustv.utils.enumOfOrDefault

internal class UserPreferencesMapper : IMapper<UserPreferencesDTO, UserPreferenceBO> {

    override fun mapInToOut(input: UserPreferencesDTO): UserPreferenceBO = with(input) {
        UserPreferenceBO(
            enableSearch = enableSearch,
            epgViewMode = enumOfOrDefault({ it.value == epgViewMode}, EpgViewModeEnum.NOW_AND_SCHEDULE),
        )
    }

    override fun mapInListToOutList(input: Iterable<UserPreferencesDTO>): Iterable<UserPreferenceBO> =
        input.map(::mapInToOut)

    override fun mapOutToIn(input: UserPreferenceBO): UserPreferencesDTO = with(input) {
        UserPreferencesDTO(
            enableSearch = enableSearch,
            epgViewMode = epgViewMode.value
        )
    }

    override fun mapOutListToInList(input: Iterable<UserPreferenceBO>): Iterable<UserPreferencesDTO> =
        input.map(::mapOutToIn)
}