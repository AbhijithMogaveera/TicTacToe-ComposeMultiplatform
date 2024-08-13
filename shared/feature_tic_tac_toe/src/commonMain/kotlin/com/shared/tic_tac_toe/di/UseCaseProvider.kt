package com.shared.tic_tac_toe.di

import com.shared.tic_tac_toe.domain.useCases.UseCaseConnectionStateChange
import com.shared.tic_tac_toe.domain.useCases.UseCaseReqPlayerPlayWithMe
import com.shared.tic_tac_toe.domain.useCases.UseCaseGetAllPlayers
import com.shared.tic_tac_toe.domain.useCases.UseCaseRespondToPlayWithMeRequest
import com.shared.tic_tac_toe.domain.useCases.TicTacToeSessionHandler
import com.shared.tic_tac_toe.domain.useCases.UseCaseGameSession
import com.shared.tic_tac_toe.domain.useCases.UseCaseNotifyRejectedPlayRequest
import com.shared.tic_tac_toe.domain.useCases.UseCaseRevokePlayRequest
import com.shared.tic_tac_toe.domain.useCases.UseCaseStopGame
import com.shared.tic_tac_toe.domain.useCases.UseCaseTapTile
import org.koin.dsl.module

val UseCaseProvider = module {

    single<TicTacToeSessionHandler> {
        TicTacToeSessionHandler(sessionHandler = get())
    }

    single<UseCaseRevokePlayRequest> {
        UseCaseRevokePlayRequest(sessionHandler = get())
    }

    single<UseCaseGetAllPlayers> {
        UseCaseGetAllPlayers(sessionHandler = get(), profileDetails = get())
    }

    single<UseCaseReqPlayerPlayWithMe> {
        UseCaseReqPlayerPlayWithMe(sessionHandler = get())
    }

    single<UseCaseRespondToPlayWithMeRequest> {
        UseCaseRespondToPlayWithMeRequest(sessionHandler = get())
    }

    single<UseCaseGameSession> {
        UseCaseGameSession(sessionHandler = get(), profileDetails = get())
    }

    single<UseCaseNotifyRejectedPlayRequest> {
        UseCaseNotifyRejectedPlayRequest(sessionHandler = get())
    }

    single<UseCaseStopGame> {
        UseCaseStopGame(sessionHandler = get())
    }

    single<UseCaseTapTile> {
        UseCaseTapTile(sessionHandler = get())
    }

    single<UseCaseConnectionStateChange> {
        UseCaseConnectionStateChange(sessionHandler = get())
    }

}