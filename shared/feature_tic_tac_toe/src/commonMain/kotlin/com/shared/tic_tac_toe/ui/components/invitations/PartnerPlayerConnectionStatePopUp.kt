package com.shared.tic_tac_toe.ui.components.invitations

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.abhijith.cmm.feature_tictactoe.Res
import com.abhijith.cmm.feature_tictactoe.game_looser
import com.abhijith.cmm.feature_tictactoe.game_winner
import com.abhijith.cmm.feature_tictactoe.not_found
import com.abhijith.cmm.feature_tictactoe.waiting
import com.shared.tic_tac_toe.domain.viewmodels.TicTacToeViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi

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
