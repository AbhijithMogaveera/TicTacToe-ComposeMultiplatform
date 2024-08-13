package com.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shared.auth.ui.hooks.AuthProtected
import com.shared.auth.ui.setupAuthNavigation
import com.shared.compose_foundation.AppTheme
import com.shared.compose_foundation.navigation.navigateIfNotCurrent
import com.shared.feature_profile.ui.ProfileComponent
import com.shared.tic_tac_toe.setUpTicTacToeNavigation

@Composable
fun AppContent() {
    TicTacToeTheme {
        Scaffold {
            Surface(
                modifier = Modifier.fillMaxSize().background(AppTheme.Background).padding(it),
            ) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "/profile",
                    modifier = Modifier.background(AppTheme.Background)
                ) {
                    setupAuthNavigation(navController)
                    setUpTicTacToeNavigation(navController)
                    composable("/profile") {
                        AuthProtected(
                            ifNotLogin = {
                                navController.navigate("/auth")
                            }
                        ) {
                            ProfileComponent(
                                onLetsPlayClick = {
                                    navController.navigateIfNotCurrent("/tic_tac_toe")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}