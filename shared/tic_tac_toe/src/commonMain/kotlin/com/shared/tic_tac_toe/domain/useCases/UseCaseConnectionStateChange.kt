package com.shared.tic_tac_toe.domain.useCases

import kotlinx.coroutines.flow.Flow

class UseCaseConnectionStateChange(
    val socketMediator:UseCaseSocketToUseCaseMediator
) {

    suspend operator fun invoke():Flow<ConnectionState> {
        return socketMediator.getConnectionState()
    }
}