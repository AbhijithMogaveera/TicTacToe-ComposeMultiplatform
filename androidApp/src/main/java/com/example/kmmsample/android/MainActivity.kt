package com.example.kmmsample.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhijith.auth.setupAuthNavigation
import com.abhijith.auth.ui.hooks.AuthProtected
import com.abhijith.foundation.AppColors
import com.abhijith.foundation.activity.ProvideActivity
import com.abhijith.tic_tac_toe.setUpTicTacToeNavigation
import com.abhijith.tic_tac_toe.ui.components.toColorInt
import com.tictactao.profile.ui.ProfileComponent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProvideActivity {
                MyApplicationTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = AppColors.BACKGROUND
                    ) {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = "/profile"
                        ) {
                            setupAuthNavigation(navController)
                            setUpTicTacToeNavigation(navController)
                            composable("/profile"){
                                AuthProtected(
                                    ifNotLogin = {
                                        navController.navigate("/auth")
                                    }
                                ) {
                                    ProfileComponent(
                                        onLetsPlayClick = {
                                            if(navController.currentDestination?.route != "/tic_tac_toe") {
                                                navController.navigate("/tic_tac_toe")
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
