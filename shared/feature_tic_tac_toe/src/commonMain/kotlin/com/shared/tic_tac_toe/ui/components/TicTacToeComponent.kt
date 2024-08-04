package com.shared.tic_tac_toe.ui.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abhijith.cmm.feature_tictactoe.Res
import com.abhijith.cmm.feature_tictactoe.connecting
import com.shared.compose_foundation.AppColors
import com.shared.tic_tac_toe.domain.useCases.ConnectionState
import com.shared.tic_tac_toe.domain.viewmodels.TicTacToeViewModel
import com.shared.tic_tac_toe.ui.components.game.TicTacToeGame
import com.shared.tic_tac_toe.ui.components.invitations.ChoosePlayer
import com.shared.tic_tac_toe.ui.components.invitations.PartnerPlayerConnectionStatePopUp
import com.shared.tic_tac_toe.ui.components.invitations.PendingRequestBottomSheet
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun TicTacToeComponent(
    onNavigateUp: () -> Unit
) {
    Box(
        modifier = Modifier.background(color = AppColors.BACKGROUND),
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
                AnimatedVisibility(
                    visible = TicTacToeViewModel.requestState == TicTacToeViewModel.PlayRequestState.PlayStarted,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = AppColors.CONTAINER),
                        modifier = Modifier.padding(10.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        TicTacToeGame()
                    }
                }
                AnimatedVisibility(
                    visible = TicTacToeViewModel.requestState != TicTacToeViewModel.PlayRequestState.PlayStarted,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    ChoosePlayer(onNavigateUp)
                    PartnerPlayerConnectionStatePopUp()
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
                IconButton(
                    onClick = onNavigateUp,
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
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