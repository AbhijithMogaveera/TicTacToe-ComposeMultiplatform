package com.shared.tic_tac_toe.domain.useCases

import com.shared.compose_foundation.ktor.socket.serializer
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement

class UseCaseRevokePlayRequest(
   private val sessionHandler: TicTacToeSessionHandler
){
    @Serializable
    data class PlayRequestRevokeDTO(
        val playReqId: String
    )
    suspend fun revoke(playReqId:String){
        sessionHandler.emmit("play_request_revoke", playReqId)
    }

    suspend fun onRevoke() =
        sessionHandler.on("play_request_revoke").map {
            val response:PlayRequestRevokeDTO = serializer.decodeFromJsonElement(it)
            response.playReqId
        }

}