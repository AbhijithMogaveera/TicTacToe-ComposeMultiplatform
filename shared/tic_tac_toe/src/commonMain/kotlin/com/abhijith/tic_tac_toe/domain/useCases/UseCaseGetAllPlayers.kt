package com.abhijith.tic_tac_toe.domain.useCases

import arrow.core.Either
import arrow.core.Option
import arrow.core.left
import arrow.core.right
import com.abhijith.tic_tac_toe.domain.models.Player
import com.abhijith.tic_tac_toe.domain.repo.TicTacToeRepo

class UseCaseGetAllPlayers(
    private val repo: TicTacToeRepo
) {

    enum class Failure {
        UnAuthorized, UnKnow
    }

    suspend fun execute(searchKey:Option<String>): Either<Failure, List<Player>> {
        return when(val it = repo.getAllPlayer(searchKey)){
            is Either.Left -> {
                it.value.isClientSideError {
                    if(it.issue.key == "UnAuthorized"){
                        return Failure.UnAuthorized.left()
                    }
                }
                return  Failure.UnKnow.left()
            }
            is Either.Right -> {
                it.value.right()
            }
        }
    }

}