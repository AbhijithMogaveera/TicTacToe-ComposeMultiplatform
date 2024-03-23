package com.abhijith.tic_tac_toe.domain.models

import com.abhijith.tic_tac_toe.domain.models.dto.ParticipantDTO
import kotlinx.serialization.Serializable

@Serializable
data class ActiveParticipantsEvent(
    val event: String,
    val data: List<ParticipantDTO>
)