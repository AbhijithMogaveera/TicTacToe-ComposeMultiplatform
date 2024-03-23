package com.abhijith.tic_tac_toe.domain.useCases

import com.abhijith.foundation.ktor.socket.serializer
import com.abhijith.tic_tac_toe.domain.models.dto.ParticipantDTO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement
import kotlin.time.Duration.Companion.seconds


class UseCaseReqPlayerPlayWithMe constructor(
    val socketToUseCaseMediator: UseCaseSocketToUseCaseMediator
) {
    @Serializable
    data class PayerPlayReqResponse(
        val isAccepted: Boolean,
    )

    suspend fun ask(participantDTO: ParticipantDTO): Boolean {
        return withTimeoutOrNull(10.seconds) {
            socketToUseCaseMediator.emmit("ask_to_play", participantDTO.user_name)
            val res = socketToUseCaseMediator.on("ask_to_play").first()
            val decodeFromJsonElement = serializer.decodeFromJsonElement<PayerPlayReqResponse>(res)
            decodeFromJsonElement.isAccepted
        } ?: let { false }
    }
}