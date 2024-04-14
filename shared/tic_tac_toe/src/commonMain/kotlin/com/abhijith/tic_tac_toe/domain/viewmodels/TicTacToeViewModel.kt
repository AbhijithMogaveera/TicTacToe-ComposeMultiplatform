package com.abhijith.tic_tac_toe.domain.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import arrow.core.None
import arrow.core.Option
import arrow.core.some
import com.abhijith.foundation.viewmodel.SharedViewModel
import com.abhijith.tic_tac_toe.domain.models.PlayRequest
import com.abhijith.tic_tac_toe.data.dto.ParticipantDTO
import com.abhijith.tic_tac_toe.data.dto.BoardState
import com.abhijith.tic_tac_toe.domain.models.GameState
import com.abhijith.tic_tac_toe.domain.useCases.ConnectionState
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseConnectionStateChange
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseGameSession
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseGetAllPlayers
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseNotifyRejectedPlayRequest
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseReqPlayerPlayWithMe
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseRespondToPlayWithMeRequest
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseRevokePlayRequest
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseStopGame
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseTapTile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.inject

internal object TicTacToeViewModel : SharedViewModel {

    var connectionState: ConnectionState by mutableStateOf(ConnectionState.Connecting)
    private var _requestState: PlayRequestState by mutableStateOf(PlayRequestState.NotInitiated)
    var requestState: PlayRequestState
        get() = _requestState
        private set(value) {
            println("Update State => $_requestState")
            _requestState = value
        }
    var boardState: Option<BoardState> by mutableStateOf(None)

    private var _timer: MutableStateFlow<Option<Long>> = MutableStateFlow(None)
    var timer: StateFlow<Option<Long>> = _timer.asStateFlow()

    override val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val ucGetAllPlayer: UseCaseGetAllPlayers by inject()
    private val ucReqPlayerToPlayWithMe: UseCaseReqPlayerPlayWithMe by inject()
    private val ucRespondToPlayRequest: UseCaseRespondToPlayWithMeRequest by inject()
    private val ucGameSession: UseCaseGameSession by inject()
    private val ucNotifyRejectedPlayRequest: UseCaseNotifyRejectedPlayRequest by inject()
    private val ucRevokePlayRequest: UseCaseRevokePlayRequest by inject()
    private val ucStopGame: UseCaseStopGame by inject()
    private val ucTapTile: UseCaseTapTile by inject()
    private val ucConnectionState: UseCaseConnectionStateChange by inject()

    private val _player: MutableStateFlow<List<ParticipantDTO>> = MutableStateFlow(emptyList())
    private val _onPlayerFetchingIssue =
        MutableStateFlow<Option<UseCaseGetAllPlayers.Failure>>(None)
    private val _pendingPlayRequest: MutableStateFlow<List<PlayRequest>> =
        MutableStateFlow(emptyList())
    private val _connectionState: MutableStateFlow<ConnectionState> =
        MutableStateFlow(ConnectionState.NotConnected)

    val player = _player.asStateFlow()
    val playerFetchingIssue = _onPlayerFetchingIssue.asStateFlow()
    val pendingRequest = _pendingPlayRequest.asStateFlow()

    fun fetchPlayers(searchKey: Option<String>) {
        coroutineScope.launch {
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
        }
    }

    enum class PlayRequestState {
        Waiting, PlayStarted, Declined, Ended, NotInitiated
    }

    private var lastAskToPlayReqID: String? = null
    fun requestToPlayerToPlay(player: ParticipantDTO) {
        if(requestState == PlayRequestState.Waiting)
            return
        requestState = PlayRequestState.Waiting
        coroutineScope.launch {
            ucReqPlayerToPlayWithMe.ask(
                player,
                onIdGenerated = {
                    lastAskToPlayReqID = it
                }
            )
        }
    }

    fun lookForNextMatch() {
        coroutineScope.launch {
            requestState = PlayRequestState.NotInitiated
        }
    }

    fun revokeOnGoingReq() {
        coroutineScope.launch {
            lastAskToPlayReqID?.let {
                ucRevokePlayRequest.revoke(it);
            }
            lastAskToPlayReqID = null
            requestState = PlayRequestState.NotInitiated
        }
    }


    private suspend fun monitorNewRequest() {
        ucRespondToPlayRequest.onNewRequest { newRequest ->
            _pendingPlayRequest.update { pendingPlayRequests ->
                pendingPlayRequests + newRequest
            }
        }
    }


    private suspend fun monitorInvitationRejection() {
        coroutineScope.launch {
            ucNotifyRejectedPlayRequest.onReject { rejectedInvitationId ->
                _pendingPlayRequest.update { playRequests ->
                    playRequests.filter { playRequest ->
                        playRequest.invitationID != rejectedInvitationId
                    }
                }
                if (rejectedInvitationId == lastAskToPlayReqID) {
                    requestState = PlayRequestState.Declined
                    lastAskToPlayReqID = null
                }
            }
        }
    }

    fun stopOnGoingGame() {
        coroutineScope.launch {
            boardState.onSome {
                ucStopGame(it.gameSessionId)
            }
        }
    }

    fun onTileClick(tileIndex: Int) {
        coroutineScope.launch {
            ucTapTile.tap(tileIndex)
        }
    }

    private suspend fun monitorInvitationRevoke() {
        ucRevokePlayRequest.onRevoke().collectLatest { revokedInvitationID ->
            _pendingPlayRequest.update { playRequests ->
                playRequests.filter { playRequest ->
                    playRequest.invitationID != revokedInvitationID
                }
            }
            if (revokedInvitationID == lastAskToPlayReqID) {
                requestState = PlayRequestState.NotInitiated
                lastAskToPlayReqID = null
            }
        }
    }


    private suspend fun monitorGameSession() {
        ucGameSession.execute().collectLatest {
            boardState = it.second.some();
            when (it.first) {
                GameState.NotStarted -> {
                    requestState = PlayRequestState.PlayStarted
                    lastAskToPlayReqID = null
                }

                GameState.OnGoing -> {
                    requestState = PlayRequestState.PlayStarted
                    lastAskToPlayReqID = null
                }

                GameState.PlayerLostAboutToEndInOneMinute -> {
                }

                GameState.End -> {
                    lookForNextMatch()
                }
            }
        }
    }

    init {
        coroutineScope.launch {
            ucConnectionState().collectLatest {
                connectionState = it
                coroutineScope {
                    when (it) {
                        ConnectionState.NotConnected -> {

                        }

                        ConnectionState.Connected -> {
                            launch { monitorGameSession() }
                            launch { monitorInvitationRevoke() }
                            launch { monitorInvitationRejection() }
                            launch { monitorNewRequest() }
                        }

                        ConnectionState.Connecting -> {

                        }
                    }
                    awaitCancellation()
                }
            }
        }
    }
}