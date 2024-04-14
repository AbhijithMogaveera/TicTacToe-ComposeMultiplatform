package com.abhijith.tic_tac_toe

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.abhijith.tic_tac_toe.ui.components.TicTacToeComponent
import com.abhijith.auth.ui.hooks.AuthProtected

fun NavGraphBuilder.setUpTicTacToeNavigation(navController:NavController){
    composable("/tic_tac_toe"){
        AuthProtected(
            ifNotLogin = {
                navController.navigate("/auth")
            }
        ) {
            TicTacToeComponent()
        }
    }
}