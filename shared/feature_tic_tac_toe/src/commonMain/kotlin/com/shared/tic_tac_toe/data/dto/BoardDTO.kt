package com.shared.tic_tac_toe.data.dto

import arrow.core.None
import arrow.core.some
import com.shared.tic_tac_toe.domain.models.GameState
import kotlinx.serialization.Serializable

@Serializable
data class BoardDTO(
    val board: BoardStateDTO,
    val gameState: GameState,
    val current_turn: String,
    val player_1: PlayerProfileDTO,
    val player_2: PlayerProfileDTO,
    val gameWillEndIn: Long? = null,
    val prematureGameTerminationBy: String? = null
) {
    fun toBoardState(): BoardState {
        board.apply {
            return BoardState(
                board = board,
                winTileDiagonalStart = winTileDiagonalStart?.some() ?: None,
                winTileDiagonalMiddle = winTileDiagonalMiddle?.some() ?: None,
                winTileDiagonalEnd = winTileDiagonalEnd?.some() ?: None,
                winPlayerUsername = winPlayerUsername?.some() ?: None,
                gameSessionId = invitation_id,
                currentTurnUsername = current_turn,
                p1Details = player_1.toPlayerDetails(),
                p2Details = player_2.toPlayerDetails(),
                gameEndsIn = gameWillEndIn?.some() ?: None,
                prematureGameTerminationBy = prematureGameTerminationBy?.some() ?: None
            )
        }
    }
}