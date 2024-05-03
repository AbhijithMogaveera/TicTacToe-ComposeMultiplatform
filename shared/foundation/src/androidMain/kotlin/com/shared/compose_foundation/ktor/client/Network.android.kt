package com.shared.compose_foundation.ktor.client

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.okhttp.OkHttp

actual fun getClient(
    config: HttpClientConfig<HttpClientEngineConfig>.() -> Unit
): HttpClient {
    return HttpClient(OkHttp, config)
}