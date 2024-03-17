package com.abhijith.tic_tac_toe.di

import com.abhijith.tic_tac_toe.domain.useCases.UseCaseReqPlayerPlayWithMe
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseGetAllPlayers
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseRespondToPlayWithMeRequest
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseSocketToUseCaseMediator
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseGameSession
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseNotifyRejectedPlayRequest
import org.koin.dsl.module

val UseCaseProvider = module {

    single<UseCaseSocketToUseCaseMediator> {
        UseCaseSocketToUseCaseMediator(
            useCaseGetAuthToken = get()
        )
    }

    single<UseCaseGetAllPlayers> {
        UseCaseGetAllPlayers(
            useCaseSocketToUseCaseMediator = get(),
            profileDetails = get()
        )
    }

    single<UseCaseReqPlayerPlayWithMe> {
        UseCaseReqPlayerPlayWithMe(
            socketToUseCaseMediator = get()
        )
    }

    single<UseCaseRespondToPlayWithMeRequest> {
        UseCaseRespondToPlayWithMeRequest(
            mediator = get()
        )
    }

    single<UseCaseGameSession> {
        UseCaseGameSession(
            mediator = get()
        )
    }

    single<UseCaseNotifyRejectedPlayRequest> {
        UseCaseNotifyRejectedPlayRequest(
            mediator = get()
        )
    }
}