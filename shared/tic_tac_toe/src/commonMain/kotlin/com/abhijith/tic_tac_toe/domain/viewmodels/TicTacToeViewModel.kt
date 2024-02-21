package com.abhijith.tic_tac_toe.domain.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import arrow.core.None
import arrow.core.Option
import arrow.core.some
import com.abhijith.foundation.viewmodel.SharedViewModel
import com.abhijith.tic_tac_toe.domain.models.Player
import com.abhijith.tic_tac_toe.domain.useCases.UseCaseGetAllPlayers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object TicTacToeViewModel : SharedViewModel, KoinComponent {

    var isGameIsStarted by mutableStateOf(false); private set
    var isPlayerGotSelected by mutableStateOf(false); private set

    override val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private val getAllPlayerUseCase: UseCaseGetAllPlayers by inject<UseCaseGetAllPlayers>()

    private val _player: MutableStateFlow<List<Player>> = MutableStateFlow(emptyList())
    private val _onPlayerFetchingIssue = MutableStateFlow<Option<UseCaseGetAllPlayers.Failure>>(None)

    val player = _player.asStateFlow()
    val playerFetchingIssue = _onPlayerFetchingIssue.asStateFlow()

    fun fetchPlayers(searchKey: Option<String>) {
        coroutineScope.launch {
            getAllPlayerUseCase
                .execute(searchKey)
                .onRight {
                    _player.emit(it)
                    _onPlayerFetchingIssue.emit(None)
                }.onLeft {
                    _player.emit(emptyList())
                    _onPlayerFetchingIssue.emit(it.some())
                }
        }
    }

    fun selectPlayer(player: Player){
        isPlayerGotSelected = true
        coroutineScope.launch {
            delay(3000)
            isGameIsStarted = true
        }
    }

}