package com.abhijith.tic_tac_toe.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GameDataDTO(
    val game: GameDTO,
    val player: List<PlayerDTO>
)