package com.abhijith.tic_tac_toe.di

import com.abhijith.tic_tac_toe.domain.useCases.UseCaseReqPlayerPlayWithMe
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseGetAllPlayers
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseRespondToPlayWithMeRequest
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseSocketToUseCaseMediator
import org.koin.dsl.module

val UseCaseProvider = module {

    single<UseCaseSocketToUseCaseMediator> {
        UseCaseSocketToUseCaseMediator(
            useCaseGetAuthToken = get()
        )
    }

    single<UseCaseGetAllPlayers> {
        UseCaseGetAllPlayers(
            useCaseSocketToUseCaseMediator = get()
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
}