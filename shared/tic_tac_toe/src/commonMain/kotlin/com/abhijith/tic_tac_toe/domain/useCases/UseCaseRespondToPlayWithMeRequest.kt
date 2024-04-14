package com.abhijith.tic_tac_toe.domain.useCases

import com.abhijith.foundation.ktor.socket.serializer
import com.abhijith.tic_tac_toe.domain.models.PlayRequest
import com.abhijith.tic_tac_toe.data.dto.PlayRequestDTO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.decodeFromJsonElement

class UseCaseRespondToPlayWithMeRequest(
    val socketMediator: UseCaseSocketToUseCaseMediator
) {
    suspend fun onNewRequest(
        onNewRequest: suspend (
            PlayRequest
        ) -> Unit
    ) {
        coroutineScope {
            socketMediator
                .on("play_request")
                .collect {
                    val requestDTO = serializer.decodeFromJsonElement<PlayRequestDTO>(it)
                    onNewRequest(
                        PlayRequest(
                            participantDTO = requestDTO.participant,
                            accept = {
                                launch {
                                    socketMediator.emmit(
                                        event = "play_request_accept",
                                        payload = requestDTO.invitationID
                                    )
                                }
                            },
                            reject = {
                                launch {
                                    socketMediator.emmit(
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
