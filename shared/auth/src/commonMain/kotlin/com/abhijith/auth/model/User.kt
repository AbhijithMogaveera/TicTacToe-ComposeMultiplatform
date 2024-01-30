package com.abhijith.auth.model

import com.abhijith.auth.apis.JwtToken

data class User(
    val userName: String,
    val token: JwtToken
)