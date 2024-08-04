package com.shared.tic_tac_toe.data.dto

import arrow.core.None
import arrow.core.Option
import com.shared.tic_tac_toe.domain.models.PlayerProfile
import com.shared.tic_tac_toe.domain.models.TileState

data class BoardState(
    val board: List<TileState> = buildList {
        repeat(9) {
            add(TileState.NONE)
        }
    },
    val winTileDiagonalStart: Option<Int> = None,
    val winTileDiagonalMiddle: Option<Int> = None,
    val winTileDiagonalEnd: Option<Int> = None,
    val winPlayerUsername: Option<String> = None,
    val prematureGameTerminationBy:Option<String> = None,
    val gameSessionId: String,
    val currentTurnUsername: String,
    val p1Details: PlayerProfile,
    val p2Details: PlayerProfile,
    val gameEndsIn: Option<Long>
) {
    fun getCurrentTurnTile(): TileState {
        if (currentTurnUsername == p1Details.user_name) {
            return p1Details.tile
        }
        if (currentTurnUsername == p2Details.user_name) {
            return p2Details.tile
        }
        throw IllegalStateException()
    }

    fun getPlayerX(): PlayerProfile {
        if (p1Details.tile == TileState.X) {
            return p1Details
        }
        if (p2Details.tile == TileState.X) {
            return p2Details
        }
        throw IllegalStateException()
    }

    fun getPlayerByUserName(username:String): PlayerProfile {
        if (p1Details.user_name == username) {
            return p1Details
        }
        if (p2Details.user_name == username) {
            return p2Details
        }
        throw IllegalStateException()
    }

    fun getPlayerO(): PlayerProfile {
        if (p1Details.tile == TileState.O) {
            return p1Details
        }
        if (p2Details.tile == TileState.O) {
            return p2Details
        }
        throw IllegalStateException()
    }
}