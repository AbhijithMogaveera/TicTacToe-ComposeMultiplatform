package com.abhijith.tic_tac_toe.domain.models

import arrow.core.Option

data class Player(
    val userId:String,
    val name:String,
    val profilePicture:Option<String>,
    val isAvailableToPlay:Boolean
)
