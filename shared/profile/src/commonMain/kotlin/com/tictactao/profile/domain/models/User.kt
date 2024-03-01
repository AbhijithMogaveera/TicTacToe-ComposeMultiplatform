package com.tictactao.profile.domain.models

import com.abhijith.auth.apis.JwtToken

data class User(
    val userName: String,
    val bio:String,
    val profilePicture:String?
)