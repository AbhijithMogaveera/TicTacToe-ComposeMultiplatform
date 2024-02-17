package com.example.fourm

import arrow.core.Either
import com.abhijith.foundation.ktor.exceptions.RequestFailure
import com.example.fourm.models.PostCreationResponse
import com.example.fourm.models.PostsResponse


interface ForumApis {

    suspend fun createPost(
        description:String
    ): Either<RequestFailure, PostCreationResponse>

    suspend fun deletePost(
        postId:String
    ): Either<RequestFailure, Unit>

    suspend fun getPosts(
        pageSize:String,
        lastFetchedPost:String
    ): Either<RequestFailure, PostsResponse>
}