package com.abhijith.tic_tac_toe.domain.useCases

import com.abhijith.foundation.ktor.socket.serializer
import com.abhijith.tic_tac_toe.domain.models.Participant
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.serialization.json.decodeFromJsonElement
import kotlin.time.Duration.Companion.minutes


class UseCaseReqPlayerPlayWithMe constructor(
    val socketToUseCaseMediator: UseCaseSocketToUseCaseMediator
) {
    data class PayerPlayReqResponse(
        val isAccepted: Boolean,
    )
    suspend fun ask(participant: Participant): Boolean {
        return withTimeoutOrNull(5.minutes) {
            socketToUseCaseMediator.emmit("ask_to_play", participant.user_name)
            val res = socketToUseCaseMediator.on("ask_to_play").first()
            val decodeFromJsonElement = serializer.decodeFromJsonElement<PayerPlayReqResponse>(res)
            println("user acceptence status => ${decodeFromJsonElement.isAccepted}")
            decodeFromJsonElement.isAccepted
        }?: let{ println("time out buddy METHOD: UseCaseAskPlayerPlayWithMe ask");false }
    }
}