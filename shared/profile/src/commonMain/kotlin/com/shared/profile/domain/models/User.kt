package com.shared.profile.domain.models

import com.shared.compose_foundation.module_config.SharedWithOtherModule

@SharedWithOtherModule
data class User(
    val userName: String,
    val bio:String,
    val profilePicture:String?
)