package com.shared.tic_tac_toe.domain.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.None
import arrow.core.Option
import arrow.core.some
import com.shared.tic_tac_toe.domain.models.PlayRequest
import com.shared.tic_tac_toe.data.dto.BoardState
import com.shared.tic_tac_toe.domain.Participant
import com.shared.tic_tac_toe.domain.models.GameState
import com.shared.tic_tac_toe.domain.useCases.ConnectionState
import com.shared.tic_tac_toe.domain.useCases.UseCaseConnectionStateChange
import com.shared.tic_tac_toe.domain.useCases.UseCaseGameSession
import com.shared.tic_tac_toe.domain.useCases.UseCaseGetAllPlayers
import com.shared.tic_tac_toe.domain.useCases.UseCaseNotifyRejectedPlayRequest
import com.shared.tic_tac_toe.domain.useCases.UseCaseReqPlayerPlayWithMe
import com.shared.tic_tac_toe.domain.useCases.UseCaseRespondToPlayWithMeRequest
import com.shared.tic_tac_toe.domain.useCases.UseCaseRevokePlayRequest
import com.shared.tic_tac_toe.domain.useCases.UseCaseStopGame
import com.shared.tic_tac_toe.domain.useCases.UseCaseTapTile
import com.shared.feature_profile.domain.use_case.UseCaseGetProfileDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal object TicTacToeViewModel : ViewModel(), KoinComponent {

    var searchText: String by mutableStateOf("")
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

    private val ucGetAllPlayer: UseCaseGetAllPlayers by inject()
    private val ucReqPlayerToPlayWithMe: UseCaseReqPlayerPlayWithMe by inject()
    private val ucRespondToPlayRequest: UseCaseRespondToPlayWithMeRequest by inject()
    private val ucGameSession: UseCaseGameSession by inject()
    private val ucNotifyRejectedPlayRequest: UseCaseNotifyRejectedPlayRequest by inject()
    private val ucRevokePlayRequest: UseCaseRevokePlayRequest by inject()
    private val ucStopGame: UseCaseStopGame by inject()
    private val ucTapTile: UseCaseTapTile by inject()
    private val ucConnectionState: UseCaseConnectionStateChange by inject()
    private val ucGetProfileDetails: UseCaseGetProfileDetails by inject()

    private val _player: MutableStateFlow<List<Participant>> = MutableStateFlow(emptyList())
    private val _onPlayerFetchingIssue =
        MutableStateFlow<Option<UseCaseGetAllPlayers.Failure>>(None)
    private val _pendingPlayRequest: MutableStateFlow<List<PlayRequest>> =
        MutableStateFlow(emptyList())
    private val _connectionState: MutableStateFlow<ConnectionState> =
        MutableStateFlow(ConnectionState.NotConnected)

    val player = _player.combine(_pendingPlayRequest) { players, pendingRequests ->
        players.map { participant ->
            participant.copy(
                isRequestingToPlay = pendingRequests
                    .any { playRequest ->
                        participant.user_name == playRequest.participant.user_name
                    }
            )
        }
    }
    val playerFetchingIssue = _onPlayerFetchingIssue.asStateFlow()
    val pendingRequest = _pendingPlayRequest.asStateFlow()

    fun fetchPlayers(searchKey: Option<String>) {
        viewModelScope.launch {
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
        Waiting, PlayStarted, Declined, NotInitiated, PrematurePlayerExit, Winner, Looser
    }

    private var lastAskToPlayReqID: String? = null
    fun requestToPlayerToPlay(player: Participant) {
        viewModelScope.launch {
            val playRequestList = pendingRequest.first()
            if (playRequestList.any { it.participant.user_name == player.user_name }) {
                playRequestList.first { it.participant.user_name == player.user_name }.accept()
                return@launch
            }
            if (requestState == PlayRequestState.Waiting) {
                return@launch
            }
            requestState = PlayRequestState.Waiting
            ucReqPlayerToPlayWithMe.ask(
                player,
                onIdGenerated = {
                    lastAskToPlayReqID = it
                }
            )
        }
    }

    fun lookForNextMatch() {
        viewModelScope.launch {
            requestState = PlayRequestState.NotInitiated
        }
    }

    fun revokeOnGoingReq() {
        viewModelScope.launch {
            lastAskToPlayReqID?.let {
                ucRevokePlayRequest.revoke(it);
            }
            lastAskToPlayReqID = null
            requestState = PlayRequestState.NotInitiated
        }
    }


    private fun monitorNewRequest(
        coroutineScope: CoroutineScope
    ) {
        coroutineScope.launch {
            val stream = ucRespondToPlayRequest.onNewRequest(coroutineScope)
            stream.collect { newRequest ->
                _pendingPlayRequest.update { pendingPlayRequests ->
                    pendingPlayRequests + newRequest
                }
            }
        }
    }


    private fun monitorInvitationRejection(
        coroutineScope: CoroutineScope
    ) {
        coroutineScope.launch {
            val stream = ucNotifyRejectedPlayRequest.onReject()
            stream.collect { rejectedInvitationId ->
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
        viewModelScope.launch {
            boardState.onSome {
                ucStopGame(it.gameSessionId)
            }
        }
    }

    fun onTileClick(tileIndex: Int) {
        viewModelScope.launch {
            ucTapTile.tap(tileIndex)
        }
    }

    private fun monitorInvitationRevoke(
        coroutineScope: CoroutineScope
    ) {
        coroutineScope.launch {
            val stream = ucRevokePlayRequest.onRevoke()
            stream.collect { revokedInvitationID ->
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
    }


    private fun monitorGameSession(
        coroutineScope: CoroutineScope
    ) {
        coroutineScope.launch {
            val stream = ucGameSession.execute()
            stream.collect { (newGameState, newBoardState) ->
                boardState = newBoardState.some();
                when (newGameState) {
                    GameState.NotStarted -> {
                        requestState = PlayRequestState.PlayStarted
                        lastAskToPlayReqID = null
                    }

                    GameState.OnGoing -> {
                        requestState = PlayRequestState.PlayStarted
                        lastAskToPlayReqID = null
                    }

                    GameState.PlayerLostAboutToEndInOneMinute -> {}

                    GameState.End -> {
                        val isOpponentQuits = (
                                    newBoardState.prematureGameTerminationBy.getOrNull() != null
                                            && newBoardState.prematureGameTerminationBy.getOrNull()
                                            != ucGetProfileDetails.getProfileDetails().first().userName
                                            && newBoardState.winPlayerUsername.isNone()
                                    )
                        val didIWon =
                            newBoardState.winPlayerUsername.getOrNull() == ucGetProfileDetails.getProfileDetails().first().userName

                        when {
                            isOpponentQuits -> {
                                _requestState = PlayRequestState.PrematurePlayerExit
                            }

                            didIWon -> {
                                _requestState = PlayRequestState.Winner
                            }

                            newBoardState.winPlayerUsername.isSome() -> {
                                _requestState = PlayRequestState.Looser
                            }

                            else -> {
                                lookForNextMatch()
                            }
                        }
                    }
                }
            }
        }
    }

    fun onSearchTextChange(s: String) {
        searchText = s
    }

    init {
        viewModelScope.launch {
            ucConnectionState.invoke().collectLatest {
                connectionState = it
                coroutineScope {
                    when (it) {
                        ConnectionState.Connecting -> {}
                        ConnectionState.NotConnected -> {}
                        ConnectionState.Connected -> {
                            monitorInvitationRevoke(this)
                            monitorInvitationRejection(this)
                            monitorNewRequest(this)
                            monitorGameSession(this)
                        }
                    }
                    awaitCancellation()
                }
            }
        }
    }
}