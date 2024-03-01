package com.abhijith.auth.apis

import arrow.core.Either
import com.abhijith.foundation.arrow.action
import com.abhijith.foundation.ktor.exceptions.RequestFailure
import com.abhijith.foundation.ktor.ensureSuccessfulRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

class AuthApisDefaultImpl constructor(
    val httpClient: HttpClient
) : AuthApis {

    override suspend fun login(
        userName: String,
        password: String
    ): Either<RequestFailure, JwtToken> = action {
        httpClient.post("/app/v1/auth/login") {
            val content = mapOf(
                "user_name" to JsonPrimitive(userName),
                "password" to JsonPrimitive(password)
            )
            setBody(JsonObject(content))
        }.ensureSuccessfulRequest().body()
    }

    override suspend fun registration(
        userName: String,
        password: String
    ): Either<RequestFailure, JwtToken> = action {
        httpClient.post("/app/v1/auth/registration") {
            val content = mapOf(
                "user_name" to JsonPrimitive(userName),
                "password" to JsonPrimitive(password)
            )
            setBody(JsonObject(content))
        }.ensureSuccessfulRequest().body()
    }

}