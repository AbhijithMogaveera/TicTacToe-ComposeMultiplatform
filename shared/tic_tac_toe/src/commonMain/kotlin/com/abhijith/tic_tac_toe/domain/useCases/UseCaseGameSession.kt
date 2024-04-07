package com.abhijith.tic_tac_toe.domain.useCases

import arrow.core.None
import arrow.core.Option
import arrow.core.some
import com.abhijith.foundation.ktor.socket.serializer
import com.tictactao.profile.domain.use_case.UseCaseGetProfileDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement

class UseCaseGameSession(
    private val socketMediator: UseCaseSocketToUseCaseMediator,
    private val profileDetails: UseCaseGetProfileDetails
) {
    suspend fun execute(): Flow<Pair<GameState, BoardState>> {
        return socketMediator
            .on("game")
            .map {
                val decodeFromJsonElement = serializer.decodeFromJsonElement<BoardDTO>(it)
                decodeFromJsonElement.gameState to decodeFromJsonElement.toBoardState()
            }
    }
}

enum class TileState {
    NONE, O, X
}

enum class GameState {
    NotStarted,
    OnGoing,
    PlayerLostAboutToEndInOneMinute,
    End
}

@Serializable
data class BoardDTO(
    val board: BoardStateDTO,
    val gameState: GameState,
    val current_turn: String,
    val player_1:PlayerProfileDTO,
    val player_2: PlayerProfileDTO
) {
    fun toBoardState(): BoardState {
        board.apply {
            return BoardState(
                board = board,
                winTileDiagonalStart = winTileDiagonalStart?.some() ?: None,
                winTileDiagonalMiddle = winTileDiagonalMiddle?.some() ?: None,
                winTileDiagonalEnd = winTileDiagonalEnd?.some() ?: None,
                winPlayerUsername = winPlayerUsername?.some() ?: None,
                gameSessionId = invitation_id,
                currentTurnUsername = current_turn,
                p1Details = player_1.toPlayerDetails(),
                p2Details = player_2.toPlayerDetails()
            )
        }
    }
}

@Serializable
data class BoardStateDTO(
    val board: List<TileState> = buildList {
        repeat(9) {
            add(TileState.NONE)
        }
    },
    val winTileDiagonalStart: Int? = null,
    val winTileDiagonalMiddle: Int? = null,
    val winTileDiagonalEnd: Int? = null,
    val winPlayerUsername: String? = null,
    val activePlayerUserName: String? = null,
    val activePlayerTile: TileState = TileState.NONE,
    val invitation_id: String

)

@Serializable
data class PlayerProfileDTO(
    val bio: String?,
    val profile_image: String?,
    val user_name: String,
    val isActive: Boolean,
    val tile: TileState
) {
    fun toPlayerDetails(): PlayerProfile {
        return PlayerProfile(
            bio = bio?.some()?:None,
            profile_image = profile_image?.some()?:None,
            user_name = user_name,
            isActive = isActive,
            tile = tile
        )
    }
}

data class PlayerProfile(
    val bio: Option<String>,
    val profile_image: Option<String>,
    val user_name: String,
    val isActive: Boolean,
    val tile: TileState
)

data class BoardState(
    val board: List<TileState> = buildList {
        repeat(9) {
            add(TileState.NONE)
        }
    },
    val winTileDiagonalStart: Option<Int> = None,
    val winTileDiagonalMiddle: Option<Int> = None,
    val winTileDiagonalEnd: Option<Int> = None,
    val winPlayerUsername: Option<String> = None,
    val gameSessionId: String,
    val currentTurnUsername: String,
    val p1Details:PlayerProfile,
    val p2Details:PlayerProfile
){
    fun getCurrentTurnTile(): TileState {
        if(currentTurnUsername == p1Details.user_name){
            return p1Details.tile
        }
        if(currentTurnUsername == p2Details.user_name){
            return p2Details.tile
        }
        throw IllegalStateException()
    }

    fun getPlayerX():PlayerProfile{
        if(p1Details.tile == TileState.X){
            return p1Details
        }
        if(p2Details.tile == TileState.X){
            return p2Details
        }
        throw IllegalStateException()
    }
    fun getPlayerO():PlayerProfile{
        if(p1Details.tile == TileState.O){
            return p1Details
        }
        if(p2Details.tile == TileState.O){
            return p2Details
        }
        throw IllegalStateException()
    }
}