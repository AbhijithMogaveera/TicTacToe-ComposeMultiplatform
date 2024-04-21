package com.abhijith.tic_tac_toe.domain.models

import com.abhijith.tic_tac_toe.data.dto.ParticipantDTO
import com.abhijith.tic_tac_toe.domain.Participant

data class PlayRequest(
    val participant: Participant,
    val accept:  () -> Unit,
    val reject:  () -> Unit,
    val invitationID:String
)