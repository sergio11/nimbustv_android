package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.model.EpgChannelBO
import com.dreamsoftware.nimbustv.domain.repository.IEpgRepository

class GetEpgDataByIdUseCase(
    private val epgRepository: IEpgRepository
) : FudgeTvUseCaseWithParams<GetEpgDataByIdUseCase.Params, List<EpgChannelBO>>() {

    override suspend fun onExecuted(params: Params): List<EpgChannelBO> =
        epgRepository.findAllChannelsByEpgId(params.id)

    data class Params(
        val id: String
    )
}