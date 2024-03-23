package com.abhijith.tic_tac_toe.domain.useCases

import com.abhijith.foundation.ktor.socket.serializer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement

class UseCaseRevokePlayRequest(
   private val socketMediator: UseCaseSocketToUseCaseMediator
){
    @Serializable
    data class PlayRequestRevokeDTO(
        val playReqId: String
    )
    suspend fun revoke(playReqId:String){
        socketMediator.emmit("play_request_revoke", playReqId)
    }

    suspend fun onRevoke() =
        socketMediator.on("play_request_revoke").map {
            val response:PlayRequestRevokeDTO = serializer.decodeFromJsonElement(it)
            response.playReqId
        }

}