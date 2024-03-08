package com.abhijith.foundation.ktor.exceptions

import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

enum class EventType {
    OFF,
    ON,
    TRANSMISSION
}
interface SocketMessage {
    fun toFrame(): Frame
}

private val serializer = Json {
    ignoreUnknownKeys = true
}

private suspend fun WebSocketSession.onEvent(event: String) {
    send(EventPayload(event, EventType.ON, Unit).toFrame())
}

private suspend fun WebSocketSession.offEvent(event: String) {
    send(EventPayload(event, EventType.OFF, Unit).toFrame())
}

@Serializable
data class EventPayload<T>(
    val event: String,
    val eventType: EventType,
    val data:T?
) : SocketMessage {

    companion object {}

    override fun toFrame(): Frame {
        return Frame.Text(serializer.encodeToString(this))
    }
}

private data class EventFrame<T>(
    val event: String,
    val message: T,
) : SocketMessage {
    override fun toFrame(): Frame {
        return Frame.Text(serializer.encodeToString(this))
    }
}

interface WebSocketUtil {

    fun getIncoming():Channel<Frame>

    suspend fun WebSocketSession.on(event: String): Flow<Frame.Text> {
        onEvent(event)
        return getIncoming()
            .receiveAsFlow()
            .filterIsInstance<Frame.Text>()
            .filter {
                val jsonPrimitive = serializer
                    .encodeToJsonElement(it.toString())
                    .jsonObject["event"]
                    ?.jsonPrimitive
                jsonPrimitive != null
                        && jsonPrimitive.isString
                        && jsonPrimitive.toString() == event
            }

    }

    fun WebSocketSession.off(event: String) {
        launch {
            offEvent(event)
        }
    }

}