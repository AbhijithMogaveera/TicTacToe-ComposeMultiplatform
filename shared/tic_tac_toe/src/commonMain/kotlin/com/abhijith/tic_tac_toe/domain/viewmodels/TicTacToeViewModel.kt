package com.abhijith.tic_tac_toe.domain.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import arrow.core.None
import arrow.core.Option
import arrow.core.some
import com.abhijith.foundation.viewmodel.SharedViewModel
import com.abhijith.tic_tac_toe.domain.models.dto.ParticipantDTO
import com.abhijith.tic_tac_toe.domain.models.PlayRequest
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseGameSession
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseReqPlayerPlayWithMe
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseGetAllPlayers
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseNotifyRejectedPlayRequest
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseRespondToPlayWithMeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal object TicTacToeViewModel : SharedViewModel, KoinComponent {

    var requestState: PlayRequestState by mutableStateOf(PlayRequestState.NotInitiated)

    override val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val ucGetAllPlayer: UseCaseGetAllPlayers by inject<UseCaseGetAllPlayers>()
    private val ucReqPlayerToPlayWithMe: UseCaseReqPlayerPlayWithMe by inject<UseCaseReqPlayerPlayWithMe>()
    private val ucRespondToPlayRequest: UseCaseRespondToPlayWithMeRequest by inject()
    private val ucGameSession: UseCaseGameSession by inject()
    private val ucNotifyRejectedPlayRequest: UseCaseNotifyRejectedPlayRequest by inject()

    private val _player: MutableStateFlow<List<ParticipantDTO>> = MutableStateFlow(emptyList())
    private val _onPlayerFetchingIssue =
        MutableStateFlow<Option<UseCaseGetAllPlayers.Failure>>(None)
    private val _pendingPlayRequest: MutableStateFlow<List<PlayRequest>> =
        MutableStateFlow(emptyList())

    val player = _player.asStateFlow()
    val playerFetchingIssue = _onPlayerFetchingIssue.asStateFlow()
    val pendingRequest = _pendingPlayRequest.asStateFlow()

    fun fetchPlayers(searchKey: Option<String>) {
        coroutineScope.launch {
            println("fetching user ------------------------")
            ucGetAllPlayer
                .execute(searchKey)
                .collect {
                    it.onRight { participants ->
                        _player.emit(participants)
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

    fun requestToPlayerToPlay(player: ParticipantDTO) {
        coroutineScope.launch {
            requestState = PlayRequestState.Waiting
            ucReqPlayerToPlayWithMe.ask(player)
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
            ucRespondToPlayRequest.onNewRequest { newRequest ->
                _pendingPlayRequest.update { pendingPlayRequests ->
                    pendingPlayRequests + newRequest
                }
            }
        }
    }


    init {
        coroutineScope.launch {
            ucGameSession.execute().collectLatest {
                _pendingPlayRequest.update { playRequests ->
                    playRequests.filter { playRequest ->
                        playRequest.invitationID != it.invitation_id
                    }
                }
                requestState = PlayRequestState.PlayStarted
            }
        }
    }

    init {
        coroutineScope.launch {
            ucNotifyRejectedPlayRequest.onReject { rejectedInvitationId ->
                _pendingPlayRequest.update { playRequests ->
                    playRequests.filter { playRequest ->
                        playRequest.invitationID != rejectedInvitationId
                    }
                }
            }
        }
    }

}