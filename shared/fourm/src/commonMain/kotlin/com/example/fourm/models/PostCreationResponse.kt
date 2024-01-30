package com.example.fourm.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostCreationResponse(
    @SerialName("post_id")
    val postId: String
)