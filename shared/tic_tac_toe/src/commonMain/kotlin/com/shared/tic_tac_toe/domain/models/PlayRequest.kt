package com.shared.tic_tac_toe.domain.models

import com.shared.tic_tac_toe.domain.Participant

data class PlayRequest(
    val participant: Participant,
    val accept:  () -> Unit,
    val reject:  () -> Unit,
    val invitationID:String
)