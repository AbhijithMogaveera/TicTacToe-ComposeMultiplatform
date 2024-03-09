package com.abhijith.tic_tac_toe.di

import com.abhijith.tic_tac_toe.domain.useCases.UseCaseGetAllPlayers
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseSocketToUseCaseMediator
import org.koin.dsl.module

val UseCaseProvider = module{
    single<UseCaseSocketToUseCaseMediator> {
        UseCaseSocketToUseCaseMediator(get())
    }
    single<UseCaseGetAllPlayers> {
        UseCaseGetAllPlayers(
            useCaseSocketToUseCaseMediator = get()
        )
    }
}