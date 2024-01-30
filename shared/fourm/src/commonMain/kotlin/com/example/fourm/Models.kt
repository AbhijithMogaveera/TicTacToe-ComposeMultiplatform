package com.example.fourm

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TokenResponse(
    @SerialName("token")
    val token: String
)