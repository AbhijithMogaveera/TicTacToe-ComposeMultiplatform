package com.shared.tic_tac_toe.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GameEventDataDTO(
    val invitation_id:String
)