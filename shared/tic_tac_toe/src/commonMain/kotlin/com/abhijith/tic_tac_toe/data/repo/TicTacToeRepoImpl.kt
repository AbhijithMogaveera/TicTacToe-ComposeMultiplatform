package com.abhijith.tic_tac_toe.data.repo

import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import com.abhijith.foundation.arrow.apiCallScope
import com.abhijith.foundation.ktor.exceptions.RequestFailure
import com.abhijith.tic_tac_toe.domain.repo.TicTacToeRepo
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseSocketToUseCaseMediator

internal class TicTacToeRepoImpl(
    private val useCaseSocketToUseCaseMediator: UseCaseSocketToUseCaseMediator
) : TicTacToeRepo {

}