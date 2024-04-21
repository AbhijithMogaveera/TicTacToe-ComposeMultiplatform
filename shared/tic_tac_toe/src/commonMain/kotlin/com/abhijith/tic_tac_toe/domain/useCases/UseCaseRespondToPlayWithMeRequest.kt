package com.abhijith.tic_tac_toe.domain.useCases

import com.abhijith.foundation.ktor.socket.serializer
import com.abhijith.tic_tac_toe.domain.models.PlayRequest
import com.abhijith.tic_tac_toe.data.dto.PlayRequestDTO
import com.abhijith.tic_tac_toe.domain.Participant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.json.decodeFromJsonElement

class UseCaseRespondToPlayWithMeRequest(
    val socketMediator: UseCaseSocketToUseCaseMediator
) {
    suspend fun onNewRequest(
        coroutineScope: CoroutineScope,
    ) = socketMediator
        .on("play_request")
        .map {
            val requestDTO = serializer.decodeFromJsonElement<PlayRequestDTO>(it)
            PlayRequest(
                participant = Participant(
                    bio = requestDTO.participant.bio,
                    profile_image = requestDTO.participant.profile_image
                        .replace("localhost", "10.0.2.2"),
                    user_name = requestDTO.participant.user_name,
                    isRequestingToPlay = true
                ),
                accept = {
                    coroutineScope.launch {
                        this@UseCaseRespondToPlayWithMeRequest.socketMediator.emmit(
                            event = "play_request_accept",
                            payload = requestDTO.invitationID
                        )
                    }
                },
                reject = {
                    coroutineScope.launch {
                        this@UseCaseRespondToPlayWithMeRequest.socketMediator.emmit(
                            event = "play_request_reject",
                            payload = requestDTO.invitationID
                        )
                    }
                },
                invitationID = requestDTO.invitationID,
            )
        }
}
