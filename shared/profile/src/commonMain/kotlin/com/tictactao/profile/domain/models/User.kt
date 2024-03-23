package com.tictactao.profile.domain.models

import com.abhijith.auth.apis.JwtToken
import com.abhijith.foundation.module_config.SharedWithOtherModule

@SharedWithOtherModule
data class User(
    val userName: String,
    val bio:String,
    val profilePicture:String?
)