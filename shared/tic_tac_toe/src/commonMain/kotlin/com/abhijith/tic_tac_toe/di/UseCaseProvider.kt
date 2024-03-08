package com.abhijith.tic_tac_toe.di

import com.abhijith.tic_tac_toe.domain.useCases.UseCaseGetAllPlayers
import com.abhijith.tic_tac_toe.domain.useCases.UserCaseTicTacToeSocketConnectionHandler
import org.koin.dsl.module

val UseCaseProvider = module{
    single<UserCaseTicTacToeSocketConnectionHandler> {
        UserCaseTicTacToeSocketConnectionHandler(get())
    }
    single<UseCaseGetAllPlayers> {
        UseCaseGetAllPlayers(
            repo = get()
        )
    }
}