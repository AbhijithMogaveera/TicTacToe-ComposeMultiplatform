package com.shared.feature_profile.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDetailsResponse(
    @SerialName("user_name")
    val userName:String,
    val bio:String,
    @SerialName("profile_picture")
    val profilePicture:String
)