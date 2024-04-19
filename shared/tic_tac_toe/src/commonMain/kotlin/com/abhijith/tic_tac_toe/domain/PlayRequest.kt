package com.abhijith.tic_tac_toe.domain

data class PlayRequest(
    val participant: Participant,
    val invitationID: String
)
