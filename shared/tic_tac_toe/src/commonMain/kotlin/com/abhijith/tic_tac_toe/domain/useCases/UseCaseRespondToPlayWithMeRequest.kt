package com.abhijith.tic_tac_toe.domain.useCases

import com.abhijith.foundation.ktor.socket.serializer
import com.abhijith.tic_tac_toe.domain.models.PlayRequest
import com.abhijith.tic_tac_toe.domain.models.dto.PlayRequestDTO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.decodeFromJsonElement

class UseCaseRespondToPlayWithMeRequest(
    val mediator: UseCaseSocketToUseCaseMediator
) {
    suspend fun onNewRequest(
        onNewRequest: suspend (
            PlayRequest
        ) -> Unit
    ) {
        coroutineScope {
            mediator
                .on("play_request")
                .collect {
                    val requestDTO = serializer.decodeFromJsonElement<PlayRequestDTO>(it)
                    onNewRequest(
                        PlayRequest(
                            participantDTO = requestDTO.participant,
                            accept = {
                                launch {
                                    mediator.emmit(
                                        event = "play_request_accept",
                                        payload = requestDTO.invitationID
                                    )
                                }
                            },
                            reject = {
                                launch {
                                    mediator.emmit(
                                        event = "play_request_reject",
                                        payload = requestDTO.invitationID
                                    )
                                }
                            },
                            invitationID = requestDTO.invitationID
                        )
                    )
                }
        }
    }

}
