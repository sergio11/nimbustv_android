package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.repository.IEpgRepository
import com.dreamsoftware.nimbustv.domain.service.IEpgSchedulerService

class DeleteEpgUseCase(
    private val epgRepository: IEpgRepository,
    private val epgSchedulerService: IEpgSchedulerService
) : FudgeTvUseCaseWithParams<DeleteEpgUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params) {
        with(params) {
            epgRepository.deleteById(id)
            epgSchedulerService.cancelSyncEpgWork(id)
        }
    }

    data class Params(
        val id: String
    )
}