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
import com.shared.compose_foundation.AppColors
import com.shared.compose_foundation.navigation.navigateSafe
import com.shared.profile.ui.ProfileComponent
import com.shared.tic_tac_toe.setUpTicTacToeNavigation

@Composable
fun AppContent() {
    MyApplicationTheme {
        Scaffold {
            Surface(
                modifier = Modifier.fillMaxSize().background(AppColors.BACKGROUND).padding(it),
            ) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "/profile"
                ) {
                    setupAuthNavigation(navController)
                    setUpTicTacToeNavigation(navController)
                    composable("/profile") {
                        AuthProtected(
                            ifNotLogin = {
                                navController.popBackStack()
                                navController.navigate("/auth")
                            }
                        ) {
                            ProfileComponent(
                                onLetsPlayClick = {
                                    navController.navigateSafe("/tic_tac_toe")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}