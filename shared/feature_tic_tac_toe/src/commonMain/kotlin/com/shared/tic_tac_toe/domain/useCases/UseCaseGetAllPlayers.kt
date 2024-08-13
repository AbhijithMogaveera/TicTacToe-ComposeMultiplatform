package com.shared.tic_tac_toe.domain.useCases

import arrow.core.Either
import arrow.core.Option
import arrow.core.right
import com.shared.compose_foundation.ktor.socket.serializer
import com.shared.compose_foundation.platform.getHostAddress
import com.shared.tic_tac_toe.domain.models.ActiveParticipantsEvent
import com.shared.tic_tac_toe.domain.Participant
import com.shared.feature_profile.domain.use_case.UseCaseGetProfileDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.decodeFromJsonElement

internal class UseCaseGetAllPlayers(
    private val sessionHandler: TicTacToeSessionHandler,
    private val profileDetails: UseCaseGetProfileDetails
) {

    enum class Failure {
        UnAuthorized, UnKnow
    }

    suspend fun execute(
        searchKey: Option<String>,
    ): Flow<Either<Failure, List<Participant>>> {
        val map = sessionHandler
            .on("activeParticipants")
            .map {
                serializer.decodeFromJsonElement<ActiveParticipantsEvent>(it).data.filter {
                    it.user_name != profileDetails.getProfileDetails().first().userName
                }.map {
                    Participant(
                        bio = it.bio,
                        profile_image = it.profile_image.replace("localhost", getHostAddress()),
                        user_name = it.user_name,
                        false
                    )
                }.right()
            }.catch {
                it.printStackTrace()
            }
        return map
    }

}