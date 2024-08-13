package com.shared.tic_tac_toe

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.shared.tic_tac_toe.ui.components.TicTacToeComponent
import com.shared.auth.ui.hooks.AuthProtected
import com.shared.compose_foundation.navigation.navigateIfNotCurrent

fun NavGraphBuilder.setUpTicTacToeNavigation(navController: NavController) {
    composable("/tic_tac_toe") {
        AuthProtected(
            ifNotLogin = { navController.navigateIfNotCurrent("/auth") }
        ) {
            TicTacToeComponent (onNavigateUp =  navController::navigateUp )
        }
    }
}