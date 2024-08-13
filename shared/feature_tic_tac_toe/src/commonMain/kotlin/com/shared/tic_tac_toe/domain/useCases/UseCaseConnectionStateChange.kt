package com.shared.tic_tac_toe.domain.useCases

import kotlinx.coroutines.flow.Flow

class UseCaseConnectionStateChange(
    val sessionHandler:TicTacToeSessionHandler
) {

    suspend operator fun invoke():Flow<ConnectionState> {
        return sessionHandler.getConnectionState()
    }
}