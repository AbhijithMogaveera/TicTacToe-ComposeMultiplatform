package com.abhijith.tic_tac_toe.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abhijith.tic_tac_toe.domain.useCases.ConnectionState
import com.abhijith.tic_tac_toe.domain.viewmodels.TicTacToeViewModel
import com.abhijith.tic_tac_toe.ui.components.game.TicTacToeGame
import com.abhijith.tic_tac_toe.ui.components.invitations.ChoosePlayer
import com.abhijith.tic_tac_toe.ui.components.invitations.GoForNextMatch
import com.abhijith.tic_tac_toe.ui.components.invitations.PartnerPlayerConnectionStatePopUp
import com.abhijith.tic_tac_toe.ui.components.invitations.PendingRequestBottomSheet
import kmmsample.shared.tic_tac_toe.generated.resources.Res
import kmmsample.shared.tic_tac_toe.generated.resources.connecting
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun TicTacToeComponent() {
    Box(
        modifier = Modifier.background(color = Color("#27374D".toColorInt())),
    ) {
        AnimatedVisibility(
            visible = TicTacToeViewModel.connectionState == ConnectionState.Connected,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                verticalArrangement = Arrangement.Top
            ) {
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
        AnimatedVisibility(
            visible = TicTacToeViewModel.connectionState != ConnectionState.Connected,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.align(Alignment.TopCenter).padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(Res.drawable.connecting),
                        contentDescription = null,
                        modifier = Modifier
                    )
                    Text("Hold On, Reaching servers", color = Color.White)
                }
            }
        }
    }

}