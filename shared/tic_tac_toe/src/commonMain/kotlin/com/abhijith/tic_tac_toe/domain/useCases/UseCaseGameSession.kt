package com.abhijith.tic_tac_toe.domain.useCases

import com.abhijith.foundation.ktor.socket.serializer
import com.abhijith.tic_tac_toe.domain.models.dto.GameEventData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.decodeFromJsonElement

class UseCaseGameSession(
    private val mediator: UseCaseSocketToUseCaseMediator
) {
    suspend fun execute(): Flow<GameEventData> {
        return mediator
            .on("game")
            .map {
                serializer.decodeFromJsonElement(it)
            }
    }
}