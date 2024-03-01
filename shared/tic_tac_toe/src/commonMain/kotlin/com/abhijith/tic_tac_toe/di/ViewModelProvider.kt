package com.abhijith.tic_tac_toe.di

import com.abhijith.tic_tac_toe.data.repo.TicTacToeRepoImpl
import com.abhijith.tic_tac_toe.domain.repo.TicTacToeRepo
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseGetAllPlayers
import org.koin.dsl.module

val ViewModelProvider = module {
    single<UseCaseGetAllPlayers> {
        UseCaseGetAllPlayers(
            repo = get()
        )
    }

    single<TicTacToeRepo> { TicTacToeRepoImpl() }
}