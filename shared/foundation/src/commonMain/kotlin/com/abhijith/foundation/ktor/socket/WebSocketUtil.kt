package com.abhijith.foundation.ktor.socket

import com.abhijith.foundation.ktor.logger.logOf
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

enum class EventType {
    OFF,
    ON,
    TRANSMISSION
}

interface SocketMessage {
    fun toFrame(): Frame
}

val serializer = Json {
    ignoreUnknownKeys = true
}

@Serializable
data class EmissionPayload<T>(
    @SerialName("event")
    val event: String,
    @SerialName("payload")
    val payLoad: T
)

interface EventPayLoad {
    @SerialName(value = "event")
    val event: String

    @SerialName(value = "eventType")
    val eventType: EventType
}

interface EventPayloadWithData<T> : EventPayLoad {
    val data: T
}


@Serializable
private data class OnOffEventPayload constructor(
    override val event: String,
    override val eventType: EventType,
) : SocketMessage, EventPayLoad {

    override fun toFrame(): Frame {
        return Frame.Text(serializer.encodeToString(this))
    }
}

@Serializable
data class Event(
    val event: String
)

interface WebSocketUtil {

    fun getIncoming(): Channel<Frame>

    suspend fun WebSocketSession.on(event: String): Flow<JsonObject> {
        event logOf "ON"
        send(OnOffEventPayload(event, EventType.ON).toFrame())
        return getIncoming()
            .receiveAsFlow()
            .onEach {
                if (it is Frame.Text) {
                    event logOf it.readText()
                }
            }
            .filterIsInstance<Frame.Text>()
            .filter {
                try {
                    serializer.decodeFromString<Event>(it.readText()).event == event
                } catch (e: Exception) {
                    event logOf e
                    false
                }
            }.mapNotNull { frame ->
                try {
                    serializer.decodeFromString<JsonObject>(frame.readText())
                } catch (e: Exception) {
                    event logOf e
                    null
                }
            }

    }

    suspend fun WebSocketSession.off(event: String) {
        send(OnOffEventPayload(event, EventType.OFF).toFrame())
    }

    fun errorInActiveSocket(): Nothing = error("Socket session is not active")
}