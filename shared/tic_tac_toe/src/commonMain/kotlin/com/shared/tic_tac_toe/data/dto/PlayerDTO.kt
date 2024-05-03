package com.shared.tic_tac_toe.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlayerDTO(
    val user_name: String,
    val symbol: Int
)