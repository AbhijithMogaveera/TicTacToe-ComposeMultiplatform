package com.abhijith.tic_tac_toe.domain.useCases

import arrow.core.Either
import com.abhijith.auth.viewmodel.usecases.UseCaseGetAuthToken
import com.abhijith.foundation.ktor.logger.asTagAndLog
import com.abhijith.foundation.ktor.socket.EmissionPayload
import com.abhijith.foundation.ktor.socket.WebSocketUtil
import com.abhijith.foundation.ktor.socket.serializer
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.websocket.ClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.ws
import io.ktor.websocket.Frame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.JsonObject
import kotlin.time.Duration.Companion.seconds

private enum class ConnectionState {
    NotConnected,
    Connected,
    Connecting
}

class UseCaseSocketToUseCaseMediator(
    private val socketMediator: UseCaseGetAuthToken
) : WebSocketUtil {
    private var scope = CoroutineScope(Dispatchers.IO+ SupervisorJob())
    private var isConnected: MutableStateFlow<ConnectionState> =
        MutableStateFlow(ConnectionState.NotConnected)
    private val client = HttpClient {
        install(WebSockets)
        install(HttpTimeout) {
            requestTimeoutMillis = 5.seconds.inWholeMicroseconds
        }
    }
    var session: ClientWebSocketSession? = null
    private var framesFlow: MutableSharedFlow<Frame> = MutableSharedFlow()
    suspend inline fun <reified T> emmit(event: String, payload: T) {
        event asTagAndLog payload
        val encodeToString = serializer.encodeToString(EmissionPayload(event, payload))
        session?.send(Frame.Text(encodeToString))
    }

    suspend fun on(event: String): Flow<JsonObject> {
        awaitConnect()
        return session?.on(event) ?: errorInActiveSocket()
    }

    private suspend fun awaitConnect() {
        if (isConnected.value == ConnectionState.NotConnected) {
            connect()
        }
        isConnected.first { state ->
            state == ConnectionState.Connected
        }
    }

    suspend fun off(event: String) {
        session?.off(event)
    }


    private suspend fun connect() {
        isConnected.emit(ConnectionState.Connecting)
        scope.launch {
            socketMediator.getToken().collectLatest { optionOption ->
                optionOption.onSome { token ->
                    connectWithToken(token)
                }
            }
        }
    }

    private suspend fun connectWithToken(token: String) {
        var retryCount = 0
        while (true) {
            Either
                .catch {
                    client.ws(
                        urlString = "ws://10.0.2.2:5036/",
                        request = {
                            headers["Authorization"] = "Bearer $token"
                        }
                    ) {
                        println("ConnectionState: connected")
                        require(session==null)
                        session = this
                        isConnected.emit(ConnectionState.Connected)
                        while (true) {
                            val received = incoming.receive() as? Frame ?: break
                            println("SocketMediator => $received")
                            launch {
                                framesFlow.emit(received)
                            }
                        }
                    }
                }
                .onLeft {
                    println("ConnectionState: disconnected retry: ${retryCount++}" + it.stackTraceToString())
                    it.printStackTrace()
                }.onRight {
                    println("ConnectionState: disconnected retry: ${retryCount++}")
                }
            session = null
            delay( /*retryDelay = */1000)
        }
    }

    fun isConnected(): Boolean {
        return client.isActive
    }

    override fun getIncoming(): Flow<Frame> {
        return framesFlow
    }
}