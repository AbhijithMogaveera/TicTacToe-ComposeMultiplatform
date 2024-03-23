package com.abhijith.tic_tac_toe.di

import com.abhijith.tic_tac_toe.domain.useCases.UseCaseReqPlayerPlayWithMe
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseGetAllPlayers
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseRespondToPlayWithMeRequest
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseSocketToUseCaseMediator
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseGameSession
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseNotifyRejectedPlayRequest
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseRevokePlayRequest
import org.koin.dsl.module

val UseCaseProvider = module {

    single<UseCaseSocketToUseCaseMediator> {
        UseCaseSocketToUseCaseMediator(
            socketMediator = get()
        )
    }

    single<UseCaseRevokePlayRequest> {
        UseCaseRevokePlayRequest(socketMediator = get())
    }

    single<UseCaseGetAllPlayers> {
        UseCaseGetAllPlayers(
            socketMediator = get(),
            profileDetails = get()
        )
    }

    single<UseCaseReqPlayerPlayWithMe> {
        UseCaseReqPlayerPlayWithMe(
            socketMediator = get()
        )
    }

    single<UseCaseRespondToPlayWithMeRequest> {
        UseCaseRespondToPlayWithMeRequest(
            socketMediator = get()
        )
    }

    single<UseCaseGameSession> {
        UseCaseGameSession(
            socketMediator = get()
        )
    }

    single<UseCaseNotifyRejectedPlayRequest> {
        UseCaseNotifyRejectedPlayRequest(
            socketMediator = get()
        )
    }
}