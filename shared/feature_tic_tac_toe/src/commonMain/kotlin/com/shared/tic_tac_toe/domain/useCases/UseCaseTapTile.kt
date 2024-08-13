package com.shared.tic_tac_toe.domain.useCases


class UseCaseTapTile(
    val sessionHandler: TicTacToeSessionHandler
) {
    suspend fun tap(position: Int) {
        sessionHandler.emmit("game_tap_tile", position)
    }
}