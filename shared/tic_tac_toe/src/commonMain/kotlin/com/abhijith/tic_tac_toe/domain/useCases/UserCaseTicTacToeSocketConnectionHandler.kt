package com.abhijith.tic_tac_toe.domain.useCases

import arrow.core.Either
import com.abhijith.auth.viewmodel.usecases.UseCaseGetAuthToken
import com.abhijith.foundation.ktor.exceptions.EventPayload
import com.abhijith.foundation.ktor.exceptions.EventType
import com.abhijith.foundation.ktor.exceptions.WebSocketUtil
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.websocket.ClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.ws
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds


internal class UserCaseTicTacToeSocketConnectionHandler(
    val useCaseGetAuthToken: UseCaseGetAuthToken
) :WebSocketUtil{
    val client = HttpClient {
        install(WebSockets)
        install(HttpTimeout) {
            requestTimeoutMillis = 5.seconds.inWholeMicroseconds
        }
    }
    var session: ClientWebSocketSession? = null
    private var framesFlow:Channel<Frame> = Channel()
    suspend fun sendMessage() {
        session?.send(Frame.Text(""))
    }

    suspend fun connect() = flow<Any> {
        useCaseGetAuthToken.getToken().collectLatest { optionOption ->
            optionOption.onSome { token ->
                Either.catch {
                    client.ws(
                        urlString = "ws://10.0.2.2:5036/",
                        request = {
                            headers["Authorization"] = "Bearer $token"
                        }
                    ) {
                        session = this
                        send(EventPayload("event", EventType.OFF, Unit).toFrame())
                        launch {
                            on("activeParticipants")
                                .collectLatest{
                                    println(it.readText())
                                }
                        }
                        while (true) {
                            val received = incoming.receive() as? Frame ?: break
                            framesFlow.send(received)
                        }
                        framesFlow.close()
                        delay(10000)
                        session = null
                    }
                }.onLeft {
                    it.printStackTrace()
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