package com.abhijith.tic_tac_toe.domain.useCases

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.json.jsonPrimitive

class UseCaseNotifyRejectedPlayRequest(
    val mediator: UseCaseSocketToUseCaseMediator
) {
    suspend fun onReject(
        onReject:(String)->Unit
    ) {
        coroutineScope {
            mediator
                .on("play_request_reject")
                .collectLatest {
                    onReject(it.jsonPrimitive.toString())
                }
        }
    }
}