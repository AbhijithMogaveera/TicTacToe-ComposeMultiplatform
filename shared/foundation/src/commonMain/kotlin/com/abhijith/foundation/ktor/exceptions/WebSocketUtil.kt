package com.abhijith.foundation.ktor.exceptions

import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
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

val serializer = Json {
    ignoreUnknownKeys = true
}

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

private data class EventFrame<T>(
    val event: String,
    val message: T,
) : SocketMessage {
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
        send(OnOffEventPayload(event, EventType.ON).toFrame())
        return getIncoming()
            .receiveAsFlow()
            .onEach {
                println((it as? Frame.Text)?.readText())
            }
            .filterIsInstance<Frame.Text>()
            .filter {
                try {
                    serializer.decodeFromString<Event>(it.readText()).event == event
                } catch (e: Exception) {
                    false
                }
            }.mapNotNull {
                try {
                    serializer.decodeFromString<JsonObject>(it.readText())
                } catch (e: Exception) {
                    null
                }
            }

    }

    suspend fun WebSocketSession.off(event: String) {
        send(OnOffEventPayload(event, EventType.OFF).toFrame())
    }

    fun errorInActiveSocket(): Nothing = error("Socket session is not active")
}