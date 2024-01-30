package com.abhijith.foundation.ktor

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Issue(
    @SerialName("message")
    val message: String,
    @SerialName("key")
    val key: String
)

open class HttpException(
    val response: HttpResponse,
    message: String,
) : Exception(message)

class ClientSideError(
    response: HttpResponse,
    val issue: Issue
) : HttpException(response, "Client side error")

class ServerSideError(
    response: HttpResponse
) : HttpException(response, "Server side error")

suspend fun HttpResponse.ensureSuccessfulRequest(): HttpResponse = apply {
    when (status.value) {
        in 200..299 -> return@apply
        in 400..499 -> throw ClientSideError(this, body())
        in 500..599 -> throw ServerSideError(this)
        else -> throw HttpException(this, "Unknown error")
    }
}