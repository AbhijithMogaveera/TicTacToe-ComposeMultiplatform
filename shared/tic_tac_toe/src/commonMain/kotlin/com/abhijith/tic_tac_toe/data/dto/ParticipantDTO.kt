package com.abhijith.tic_tac_toe.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ParticipantDTO(
    val bio: String,
    val profile_image: String,
    val user_name: String
)