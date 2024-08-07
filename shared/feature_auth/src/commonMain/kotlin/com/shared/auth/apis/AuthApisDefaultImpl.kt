package com.shared.auth.apis

import arrow.core.Either
import com.shared.compose_foundation.arrow.apiCallScope
import com.shared.compose_foundation.ktor.exceptions.RequestFailure
import com.shared.compose_foundation.ktor.ensureSuccessfulRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

internal class AuthApisDefaultImpl(
    val httpClient: HttpClient
) : AuthApis {

    override suspend fun login(
        userName: String,
        password: String
    ): Either<RequestFailure, JwtToken> = apiCallScope {
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
    ): Either<RequestFailure, JwtToken> = apiCallScope {
        httpClient.post("/app/v1/auth/registration") {
            val content = mapOf(
                "user_name" to JsonPrimitive(userName),
                "password" to JsonPrimitive(password)
            )
            setBody(JsonObject(content))
        }.ensureSuccessfulRequest().body()
    }

}