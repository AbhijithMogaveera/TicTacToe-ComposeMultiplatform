package com.abhijith.tic_tac_toe.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Participant(
    val bio: String,
    val profile_image: String,
    val user_name: String
)

@Serializable
data class ActiveParticipantsEvent(
    val event: String,
    val data: List<Participant>
)