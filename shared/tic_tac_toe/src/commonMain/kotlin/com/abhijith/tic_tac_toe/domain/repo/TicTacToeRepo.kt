package com.abhijith.tic_tac_toe.domain.repo

import arrow.core.Either
import arrow.core.Option
import com.abhijith.foundation.ktor.exceptions.RequestFailure
import com.abhijith.tic_tac_toe.domain.models.Player

interface TicTacToeRepo {

    suspend fun getAllPlayer(
        searchKey:Option<String>
    ): Either<RequestFailure, List<Player>>

}