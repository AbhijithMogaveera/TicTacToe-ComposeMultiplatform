package com.abhijith.tic_tac_toe.domain.models

import arrow.core.Option

data class PlayerProfile(
    val bio: Option<String>,
    val profile_image: Option<String>,
    val user_name: String,
    val isActive: Boolean,
    val tile: TileState
)