package com.shared.tic_tac_toe.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GameDTO(
    val board: List<List<Int>>,
    val currentPlayer: Int
)

