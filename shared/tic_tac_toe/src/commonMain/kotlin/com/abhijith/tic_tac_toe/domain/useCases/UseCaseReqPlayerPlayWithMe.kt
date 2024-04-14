package com.abhijith.tic_tac_toe.domain.useCases

import com.abhijith.foundation.ktor.socket.serializer
import com.abhijith.tic_tac_toe.data.dto.ParticipantDTO
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement
import kotlin.time.Duration.Companion.minutes


class UseCaseReqPlayerPlayWithMe constructor(
    val socketMediator: UseCaseSocketToUseCaseMediator
) {
    @Serializable
    data class PayerPlayReqResponse(
        val playRequestId: String,
    )

    suspend fun ask(participantDTO: ParticipantDTO, onIdGenerated:(String)->Unit) {
        withTimeoutOrNull<Unit>(1.minutes) {
            socketMediator.emmit("ask_to_play", participantDTO.user_name)
            val res = socketMediator.on("ask_to_play").first()
            val decodeFromJsonElement = serializer.decodeFromJsonElement<PayerPlayReqResponse>(res)
            onIdGenerated(decodeFromJsonElement.playRequestId)
            awaitCancellation()
        }
    }
}