package com.abhijith.tic_tac_toe.domain.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val board: List<List<Int>>,
    val currentPlayer: Int
)

@Serializable
data class Player(
    val user_name: String,
    val symbol: Int
)

@Serializable
data class GameEventData(
    val invitation_id:String
)

@Serializable
data class GameData(
    val game: Game,
    val player: List<Player>
)