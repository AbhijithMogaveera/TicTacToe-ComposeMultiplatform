package com.shared.tic_tac_toe.domain.useCases

class UseCaseStopGame(
    private val sessionHandler: TicTacToeSessionHandler
) {
    suspend operator fun invoke(gameKey: String) {
        sessionHandler.emmit("game_stop", gameKey)
    }
}