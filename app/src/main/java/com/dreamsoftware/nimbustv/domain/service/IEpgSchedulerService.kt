package com.dreamsoftware.nimbustv.domain.service

interface IEpgSchedulerService {

    fun scheduleSyncEpgWork(epgId: String)

    fun cancelSyncEpgWork(epgId: String)
}