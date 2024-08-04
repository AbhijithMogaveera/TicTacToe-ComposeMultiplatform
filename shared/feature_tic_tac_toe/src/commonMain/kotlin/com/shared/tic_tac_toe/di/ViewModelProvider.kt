package com.shared.tic_tac_toe.di

import com.shared.tic_tac_toe.data.repo.TicTacToeRepoImpl
import com.shared.tic_tac_toe.domain.repo.TicTacToeRepo
import org.koin.dsl.module

val ViewModelProvider = module {
    single<TicTacToeRepo> {
        TicTacToeRepoImpl(
            get()
        )
    }
}