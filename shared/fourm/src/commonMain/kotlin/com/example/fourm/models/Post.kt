package com.example.fourm.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    @SerialName("_id")
    val id: String,
    @SerialName("posted_by")
    val postedBy: String,
    @SerialName("description")
    val description: String,
    @SerialName("posted_on")
    val postedOn: Long,
)

@Serializable
data class PostsResponse(
    @SerialName("posts")
    val posts: List<Post>
)