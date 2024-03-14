package com.abhijith.tic_tac_toe.domain.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import arrow.core.None
import arrow.core.Option
import arrow.core.some
import com.abhijith.foundation.viewmodel.SharedViewModel
import com.abhijith.tic_tac_toe.domain.models.Participant
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseReqPlayerPlayWithMe
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseGetAllPlayers
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseRespondToPlayWithMeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

internal object TicTacToeViewModel : SharedViewModel, KoinComponent {
    val useCaseRespondToPlayWithMeRequest: UseCaseRespondToPlayWithMeRequest by inject()
    var requestState: PlayRequestState by mutableStateOf(PlayRequestState.NotInitiated)

    override val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private val getAllPlayerUseCase: UseCaseGetAllPlayers by inject<UseCaseGetAllPlayers>()
    private val askPlayerToPlayWithMe: UseCaseReqPlayerPlayWithMe by inject<UseCaseReqPlayerPlayWithMe>()

    private val _player: MutableStateFlow<List<Participant>> = MutableStateFlow(emptyList())
    private val _onPlayerFetchingIssue =
        MutableStateFlow<Option<UseCaseGetAllPlayers.Failure>>(None)

    val player = _player.asStateFlow()
    val playerFetchingIssue = _onPlayerFetchingIssue.asStateFlow()

    fun fetchPlayers(searchKey: Option<String>) {
        coroutineScope.launch {
            println("fetching user ------------------------")
            getAllPlayerUseCase
                .execute(searchKey)
                .collect {
                    it.onRight {
                        _player.emit(it)
                        _onPlayerFetchingIssue.emit(None)
                    }.onLeft {
                        _player.emit(emptyList())
                        _onPlayerFetchingIssue.emit(it.some())
                    }
                }
            println("collection done ------------------------")
        }
    }

    enum class PlayRequestState {
        Waiting, PlayStarted, Declined, Error, Ended, NotInitiated
    }

    fun requestToPlayerToPlay(player: Participant) {
        coroutineScope.launch {
            requestState = PlayRequestState.Waiting
            delay(3.seconds)
            requestState = if (askPlayerToPlayWithMe.ask(player)) {
                coroutineScope.launch {
                    delay(3.seconds)
                    PlayRequestState.Ended
                }
                PlayRequestState.PlayStarted
            } else {
                if (Random.nextBoolean()) {
                    PlayRequestState.Declined
                } else {
                    PlayRequestState.Error
                }
            }
        }
    }

    fun lookForNextMatch() {
        coroutineScope.launch {
            requestState = PlayRequestState.NotInitiated
        }
    }

    fun endGame() {
        requestState = PlayRequestState.Ended
    }

    init {
        coroutineScope.launch {
            useCaseRespondToPlayWithMeRequest.execute(
                onEachRequest = { player, accept, reject ->
                    if (Random.nextBoolean()) {
                        accept()
                    } else {
                        reject()
                    }
                }
            )
        }
    }
}