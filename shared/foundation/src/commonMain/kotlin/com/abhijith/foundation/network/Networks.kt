package com.abhijith.foundation.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.http.content.PartData
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

expect fun getClient(
    config: HttpClientConfig<HttpClientEngineConfig>.() -> Unit
): HttpClient

val httpClient: HttpClient by lazy {
    getClient(
        config = {
            defaultRequest {
                headers["Content-Type"] = "application/json"
                host = "10.0.2.2"
//                host = "192.168.133.246"
                port = 8000
            }
            installContentNegotiation()
            installResponseObserver()
            installLogging()
        }
    )
}

private fun HttpClientConfig<*>.installContentNegotiation() {
    install(ContentNegotiation) {
        json(
            json = Json {
                prettyPrint = true
                isLenient = true
            }
        )
    }
}

private fun HttpClientConfig<*>.installLogging() {
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println("HttpClient => $message")
            }
        }
        level = LogLevel.ALL
    }
}

private fun HttpClientConfig<*>.installResponseObserver() {
    install(ResponseObserver) {
        onResponse {
            println("HttpClient => $it")
        }
    }
}
