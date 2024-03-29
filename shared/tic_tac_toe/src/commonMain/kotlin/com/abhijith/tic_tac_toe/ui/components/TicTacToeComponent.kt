package com.abhijith.tic_tac_toe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.abhijith.tic_tac_toe.domain.viewmodels.TicTacToeViewModel
import com.abhijith.tic_tac_toe.ui.components.game.TicTacToeGame
import com.abhijith.tic_tac_toe.ui.components.invitations.ChoosePlayer
import com.abhijith.tic_tac_toe.ui.components.invitations.GoForNextMatch
import com.abhijith.tic_tac_toe.ui.components.invitations.PartnerPlayerConnectionStatePopUp
import com.abhijith.tic_tac_toe.ui.components.invitations.PendingRequestBottomSheet
import com.abhijith.tic_tac_toe.ui.components.toColorInt

@Composable
internal fun TicTacToeComponent() {
    Column(
        modifier = Modifier.background(color = Color("#27374D".toColorInt())),
        verticalArrangement = Arrangement.Top
    ) {
        TicTacToeGame()
        return
        PendingRequestBottomSheet()
        if (TicTacToeViewModel.requestState != TicTacToeViewModel.PlayRequestState.PlayStarted) {
            if (TicTacToeViewModel.requestState == TicTacToeViewModel.PlayRequestState.Ended) {
                GoForNextMatch()
            } else {
                ChoosePlayer()
                PartnerPlayerConnectionStatePopUp()
            }
        } else {
            TicTacToeGame()
        }
    }
}