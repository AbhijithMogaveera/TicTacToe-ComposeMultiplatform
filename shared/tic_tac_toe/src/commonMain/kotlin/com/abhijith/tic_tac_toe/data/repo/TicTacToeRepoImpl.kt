package com.abhijith.tic_tac_toe.data.repo

import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import com.abhijith.foundation.arrow.apiCallScope
import com.abhijith.foundation.ktor.exceptions.RequestFailure
import com.abhijith.tic_tac_toe.domain.repo.TicTacToeRepo
import com.abhijith.tic_tac_toe.domain.models.Player
import com.abhijith.tic_tac_toe.domain.useCases.UserCaseTicTacToeSocketConnectionHandler
import kotlinx.coroutines.flow.collect

internal class TicTacToeRepoImpl(
    private val userCaseTicTacToeSocketConnectionHandler: UserCaseTicTacToeSocketConnectionHandler
) : TicTacToeRepo {

    override suspend fun getAllPlayer(searchKey: Option<String>): Either<RequestFailure, List<Player>> = apiCallScope {
        userCaseTicTacToeSocketConnectionHandler.connect().collect()
        println("Connect complete")
        var id = 0;
        return@apiCallScope listOf(
            Player(
                userId = "abhialur8898@gmail.com" + id++,
                name = "Abhijith Mogaveera",
                isAvailableToPlay = true,
                profilePicture = None
            ),
            Player(
                userId = "abhialur8898@gmail.com" + id++,
                name = "Abhijith Mogaveera",
                isAvailableToPlay = true,
                profilePicture = None
            ),
            Player(
                userId = "abhialur8898@gmail.com" + id++,
                name = "Abhijith Mogaveera",
                isAvailableToPlay = true,
                profilePicture = None
            ),
            Player(
                userId = "abhialur8898@gmail.com" + id++,
                name = "Abhijith Mogaveera",
                isAvailableToPlay = true,
                profilePicture = None
            ),
            Player(
                userId = "abhialur8898@gmail.com" + id++,
                name = "Abhijith Mogaveera",
                isAvailableToPlay = true,
                profilePicture = None
            ),
            Player(
                userId = "abhialur8898@gmail.com" + id + 1,
                name = "Abhijith Mogaveera",
                isAvailableToPlay = true,
                profilePicture = None
            ),
        )
    }
}