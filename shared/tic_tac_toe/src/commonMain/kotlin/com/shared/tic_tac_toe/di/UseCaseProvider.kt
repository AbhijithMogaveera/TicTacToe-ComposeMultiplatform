package com.shared.tic_tac_toe.di

import com.shared.tic_tac_toe.domain.useCases.UseCaseConnectionStateChange
import com.shared.tic_tac_toe.domain.useCases.UseCaseReqPlayerPlayWithMe
import com.shared.tic_tac_toe.domain.useCases.UseCaseGetAllPlayers
import com.shared.tic_tac_toe.domain.useCases.UseCaseRespondToPlayWithMeRequest
import com.shared.tic_tac_toe.domain.useCases.UseCaseSocketToUseCaseMediator
import com.shared.tic_tac_toe.domain.useCases.UseCaseGameSession
import com.shared.tic_tac_toe.domain.useCases.UseCaseNotifyRejectedPlayRequest
import com.shared.tic_tac_toe.domain.useCases.UseCaseRevokePlayRequest
import com.shared.tic_tac_toe.domain.useCases.UseCaseStopGame
import com.shared.tic_tac_toe.domain.useCases.UseCaseTapTile
import org.koin.dsl.module

val UseCaseProvider = module {

    single<UseCaseSocketToUseCaseMediator> {
        UseCaseSocketToUseCaseMediator(socketMediator = get())
    }

    single<UseCaseRevokePlayRequest> {
        UseCaseRevokePlayRequest(socketMediator = get())
    }

    single<UseCaseGetAllPlayers> {
        UseCaseGetAllPlayers(socketMediator = get(), profileDetails = get())
    }

    single<UseCaseReqPlayerPlayWithMe> {
        UseCaseReqPlayerPlayWithMe(socketMediator = get())
    }

    single<UseCaseRespondToPlayWithMeRequest> {
        UseCaseRespondToPlayWithMeRequest(socketMediator = get())
    }

    single<UseCaseGameSession> {
        UseCaseGameSession(socketMediator = get(), profileDetails = get())
    }

    single<UseCaseNotifyRejectedPlayRequest> {
        UseCaseNotifyRejectedPlayRequest(socketMediator = get())
    }

    single<UseCaseStopGame> {
        UseCaseStopGame(socketMediator = get())
    }

    single<UseCaseTapTile> {
        UseCaseTapTile(socketMediator = get())
    }

    single<UseCaseConnectionStateChange> {
        UseCaseConnectionStateChange(socketMediator = get())
    }

}