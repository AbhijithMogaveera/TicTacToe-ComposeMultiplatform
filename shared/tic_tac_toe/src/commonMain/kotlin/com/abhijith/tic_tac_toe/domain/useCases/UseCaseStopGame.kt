package com.abhijith.tic_tac_toe.domain.useCases

class UseCaseStopGame(
    private val socketMediator: UseCaseSocketToUseCaseMediator
) {
    suspend operator fun invoke(gameKey: String) {
        socketMediator.emmit("game_stop", gameKey)
    }
}