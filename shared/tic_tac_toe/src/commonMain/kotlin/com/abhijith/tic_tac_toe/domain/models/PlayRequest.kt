package com.abhijith.tic_tac_toe.domain.models

import com.abhijith.tic_tac_toe.domain.models.dto.ParticipantDTO

data class PlayRequest(
    val participantDTO: ParticipantDTO,
    val accept:  () -> Unit,
    val reject:  () -> Unit,
    val invitationID:String
)