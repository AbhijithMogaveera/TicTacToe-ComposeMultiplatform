package com.abhijith.foundation.usecase

interface SyncUseCase {
    fun startSync()
    fun stopSyncs()
}

interface SyncUseCaseHost {

    fun getSyncUseCases(): List<SyncUseCase> {
        return emptyList()
    }

    fun startSync() {
        getSyncUseCases().forEach { it.startSync() }
    }

    fun stopSync() {
        getSyncUseCases().forEach { it.stopSyncs() }
    }

}