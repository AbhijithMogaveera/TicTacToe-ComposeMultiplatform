package com.abhijith.tic_tac_toe.domain.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlayRequestDTO(
    val event: String,
    val participant: ParticipantDTO,
    val invitationID: String
)
