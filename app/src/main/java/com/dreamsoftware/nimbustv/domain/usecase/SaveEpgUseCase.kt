package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.repository.IEpgRepository
import com.dreamsoftware.nimbustv.domain.service.IEpgParserService

class SaveEpgUseCase(
    private val epgParserService: IEpgParserService,
    private val epgRepository: IEpgRepository
) : FudgeTvUseCaseWithParams<SaveEpgUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params) {
        val data = epgParserService.parseEpgData(url = params.url)
        epgRepository.save(data)
    }

    data class Params(
        val url: String
    )
}