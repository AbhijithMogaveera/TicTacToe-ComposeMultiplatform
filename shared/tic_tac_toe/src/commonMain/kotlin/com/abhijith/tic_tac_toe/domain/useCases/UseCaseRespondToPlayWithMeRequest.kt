package com.abhijith.tic_tac_toe.domain.useCases

import com.abhijith.foundation.ktor.socket.serializer
import com.abhijith.tic_tac_toe.domain.models.Participant
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.decodeFromJsonElement

class UseCaseRespondToPlayWithMeRequest(
    val mediator: UseCaseSocketToUseCaseMediator
) {
    suspend fun execute(
        onEachRequest: suspend (
            player: Participant,
            accept: suspend () -> Unit,
            reject: suspend () -> Unit
        ) -> Unit
    ) {
        mediator
            .on("play_request")
            .collect {
                val participant = serializer.decodeFromJsonElement<Participant>(it)
                onEachRequest(
                    /*player = */participant,
                    /*accept = */{ mediator.emmit("accept_play_request", participant) },
                    /*reject = */{ mediator.emmit("decline_play_request", participant) }
                )
            }
    }
}
