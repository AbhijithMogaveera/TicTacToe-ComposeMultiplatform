package com.shared.tic_tac_toe.domain.models

import com.shared.tic_tac_toe.data.dto.ParticipantDTO
import kotlinx.serialization.Serializable

@Serializable
data class ActiveParticipantsEvent(
    val event: String,
    val data: List<ParticipantDTO>
)