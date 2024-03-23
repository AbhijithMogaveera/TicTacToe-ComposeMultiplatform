package com.abhijith.tic_tac_toe.domain.useCases

import arrow.core.Either
import arrow.core.Option
import arrow.core.right
import com.abhijith.foundation.ktor.socket.serializer
import com.abhijith.tic_tac_toe.domain.models.ActiveParticipantsEvent
import com.abhijith.tic_tac_toe.domain.models.dto.ParticipantDTO
import com.tictactao.profile.domain.use_case.UseCaseGetProfileDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.decodeFromJsonElement

internal class UseCaseGetAllPlayers(
    private val socketMediator: UseCaseSocketToUseCaseMediator,
    private val profileDetails: UseCaseGetProfileDetails
) {

    enum class Failure {
        UnAuthorized, UnKnow
    }

    suspend fun execute(
        searchKey: Option<String>
    ): Flow<Either<Failure, List<ParticipantDTO>>> {
        val map = socketMediator
            .on("activeParticipants")
            .map{
                serializer.decodeFromJsonElement<ActiveParticipantsEvent>(it).data.filter {
                    it.user_name != profileDetails.getProfileDetails().first().userName
                }.right()
            }.catch {
                it.printStackTrace()
            }
        return map
    }

}