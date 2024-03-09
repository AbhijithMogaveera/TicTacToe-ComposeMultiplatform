package com.abhijith.tic_tac_toe.domain.useCases

import arrow.core.Either
import com.abhijith.auth.viewmodel.usecases.UseCaseGetAuthToken
import com.abhijith.foundation.ktor.exceptions.WebSocketUtil
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.websocket.ClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.ws
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import kotlin.time.Duration.Companion.seconds

private enum class ConnectionState {
    NotConnected, Connected, Connecting
}

internal class UseCaseSocketToUseCaseMediator(
    private val useCaseGetAuthToken: UseCaseGetAuthToken
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
    private var session: ClientWebSocketSession? = null
    private var framesFlow: Channel<Frame> = Channel()
    suspend fun sendMessage() {
        session?.send(Frame.Text(""))
    }

    suspend fun on(event: String): Flow<JsonObject> {
        awaitConnect()
        println("added watcher for event $event")
        return session?.on(event) ?: errorInActiveSocket()
    }

    private suspend fun awaitConnect() {
        println("awaiting for connect")
        if (isConnected.value == ConnectionState.NotConnected) {
            connect()
        }
        isConnected.first { state ->
            println("awaiting $state")
            state == ConnectionState.Connected
        }
        println("awaiting finish")
    }

    suspend fun off(event: String) {
        session?.off(event)
    }

    private fun connect() {
        scope.launch {
            useCaseGetAuthToken.getToken().collectLatest { optionOption ->
                optionOption.onSome { token ->
                    Either.catch {
                        isConnected.emit(ConnectionState.Connecting)
                        println("socket status: not connecting")
                        client.ws(
                            urlString = "ws://10.0.2.2:5036/",
                            request = {
                                headers["Authorization"] = "Bearer $token"
                            }
                        ) {
                            session = this
                            isConnected.emit(ConnectionState.Connected)
                            println("socket status: connected")
                            while (true) {
                                val received = incoming.receive() as? Frame ?: break
                                println(received)
                                launch {
                                    framesFlow.send(received)
                                }
                            }
                            framesFlow.close()
                            delay(10000)
                            session = null
                        }
                    }.onLeft {
                        println("socket status: error ${it.stackTraceToString()}")
                        it.printStackTrace()
                    }
                }
            }
        }
    }

    fun isConnected(): Boolean {
        return client.isActive
    }

    override fun getIncoming(): Channel<Frame> {
        return framesFlow
    }
}