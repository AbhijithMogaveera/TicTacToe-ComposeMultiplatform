package com.example.fourm

import arrow.core.Either
import com.abhijith.foundation.arrow.action
import com.abhijith.foundation.exceptions.RequestFailure
import com.abhijith.foundation.ktor.ensureSuccessfulRequest
import com.example.fourm.models.PostCreationResponse
import com.example.fourm.models.PostsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

internal class ForumApiDefaultImpl(
    private val client: HttpClient
) : ForumApis {

    override suspend fun createPost(
        description: String
    ): Either<RequestFailure, PostCreationResponse> = action {
        client.post("/app/v1/forum/post/") {
            setBody(
                JsonObject(
                    hashMapOf(
                        "description" to JsonPrimitive(description)
                    )
                )
            )
        }.ensureSuccessfulRequest().body()
    }

    override suspend fun deletePost(
        postId: String
    ): Either<RequestFailure, Unit> {
        TODO()
    }

    override suspend fun getPosts(
        pageSize: String,
        lastFetchedPost: String
    ): Either<RequestFailure, PostsResponse> = action {
        client.get("/app/v1/forum/post/").body()
    }

}