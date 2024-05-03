package com.shared.tic_tac_toe.data.repo

import com.shared.tic_tac_toe.domain.repo.TicTacToeRepo
import com.shared.tic_tac_toe.domain.useCases.UseCaseSocketToUseCaseMediator

internal class TicTacToeRepoImpl(
    private val useCaseSocketToUseCaseMediator: UseCaseSocketToUseCaseMediator
) : TicTacToeRepo {

}