package com.abhijith.tic_tac_toe.domain

data class Participant(
    val bio: String,
    val profile_image: String,
    val user_name: String,
    val isRequestingToPlay:Boolean
)