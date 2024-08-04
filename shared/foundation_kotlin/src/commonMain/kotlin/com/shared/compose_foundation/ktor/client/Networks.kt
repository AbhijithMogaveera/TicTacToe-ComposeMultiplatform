package com.shared.compose_foundation.ktor.client

import com.shared.compose_foundation.ktor.TokenInterceptorPlugin
import com.shared.compose_foundation.platform.Platform
import com.shared.compose_foundation.platform.getHostAddress
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.PlatformUtils
import kotlinx.serialization.json.Json

expect fun getClient(
    config: HttpClientConfig<HttpClientEngineConfig>.() -> Unit
): HttpClient

val httpClient: HttpClient by lazy {
    getClient(
        config = {
            install(TokenInterceptorPlugin)
            defaultRequest {
                headers["Content-Type"] = "application/json"
                host = getHostAddress()
                port = 5035
            }
            install(HttpTimeout){
                requestTimeoutMillis = 2000
                connectTimeoutMillis = 2000
                socketTimeoutMillis = 2000
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
                ignoreUnknownKeys = true
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
