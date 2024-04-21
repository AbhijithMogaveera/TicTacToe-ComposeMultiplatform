package com.abhijith.tic_tac_toe.domain.useCases


class UseCaseTapTile(
    val socketMediator: UseCaseSocketToUseCaseMediator
) {
    suspend fun tap(position: Int) {
        socketMediator.emmit("game_tap_tile", position)
    }
}