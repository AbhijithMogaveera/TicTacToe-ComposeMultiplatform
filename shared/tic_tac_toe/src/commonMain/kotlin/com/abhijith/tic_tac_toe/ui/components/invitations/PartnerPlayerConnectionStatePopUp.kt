package com.abhijith.tic_tac_toe.ui.components.invitations

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.abhijith.tic_tac_toe.domain.viewmodels.TicTacToeViewModel
import kmmsample.shared.tic_tac_toe.generated.resources.Res
import kmmsample.shared.tic_tac_toe.generated.resources.not_found
import kmmsample.shared.tic_tac_toe.generated.resources.waiting
import kmmsample.shared.tic_tac_toe.generated.resources.game_looser
import kmmsample.shared.tic_tac_toe.generated.resources.game_winner
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PartnerPlayerConnectionStatePopUp() {

    when (TicTacToeViewModel.requestState) {
        TicTacToeViewModel.PlayRequestState.Waiting -> {
            QuickDialog(
                res = Res.drawable.waiting,
                title = "Waiting..",
                message = "Waiting for other player to accept your request",
                actions = {
                    AssistChip(onClick = {
                        TicTacToeViewModel.revokeOnGoingReq()
                    }, label = {
                        Text("Withdraw request..! 🙂", color = Color.White)
                    }, shape = CircleShape)
                },
                onDismissRequest = {}
            )
        }

        TicTacToeViewModel.PlayRequestState.Declined -> {
            QuickDialog(
                res = Res.drawable.not_found,
                title = "Request Rejected",
                message = "Your play request got rejected",
                actions = {
                    AssistChip(onClick = {
                        TicTacToeViewModel.lookForNextMatch()
                    }, label = {
                        Text("Dismiss 😢", color = Color.White)
                    }, shape = CircleShape)
                },
                onDismissRequest = {}
            )
        }

        TicTacToeViewModel.PlayRequestState.PrematurePlayerExit -> {
            QuickDialog(
                res = Res.drawable.game_looser,
                title = "Player exit",
                message = "Opps! player exited the game",
                actions = {
                    AssistChip(onClick = {
                        TicTacToeViewModel.lookForNextMatch()
                    }, label = {
                        Text("Dismiss", color = Color.White)
                    }, shape = CircleShape)
                },
                onDismissRequest = {
                    TicTacToeViewModel.lookForNextMatch()
                }
            )
        }

        TicTacToeViewModel.PlayRequestState.Winner -> {
            QuickDialog(
                res = Res.drawable.game_winner,
                title = "Hurry you won 🥳",
                message = "Winner winner chicken dinner",
                actions = {
                    AssistChip(onClick = {
                        TicTacToeViewModel.lookForNextMatch()
                    }, label = {
                        Text("Dismiss", color = Color.White)
                    }, shape = CircleShape)
                },
                onDismissRequest = {
                    TicTacToeViewModel.lookForNextMatch()
                }
            )
        }

        TicTacToeViewModel.PlayRequestState.Looser -> {
            QuickDialog(
                res = Res.drawable.game_looser,
                title = "You lost the game 😭",
                message = "Better luck next time",
                actions = {
                    AssistChip(onClick = {
                        TicTacToeViewModel.lookForNextMatch()
                    }, label = {
                        Text("Dismiss", color = Color.White)
                    }, shape = CircleShape)
                },
                onDismissRequest = {
                    TicTacToeViewModel.lookForNextMatch()
                }
            )
        }
        else -> {}
    }
}
