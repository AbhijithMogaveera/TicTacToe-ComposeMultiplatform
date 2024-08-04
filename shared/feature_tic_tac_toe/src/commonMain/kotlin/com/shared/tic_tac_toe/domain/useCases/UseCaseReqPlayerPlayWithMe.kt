package com.shared.tic_tac_toe.domain.useCases

import com.shared.compose_foundation.ktor.socket.serializer
import com.shared.tic_tac_toe.domain.Participant
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

    suspend fun ask(participantDTO: Participant, onIdGenerated:(String)->Unit) {
        withTimeoutOrNull<Unit>(1.minutes) {
            socketMediator.emmit("ask_to_play", participantDTO.user_name)
            val res = socketMediator.on("ask_to_play").first()
            val decodeFromJsonElement = serializer.decodeFromJsonElement<PayerPlayReqResponse>(res)
            onIdGenerated(decodeFromJsonElement.playRequestId)
            awaitCancellation()
        }
    }
}