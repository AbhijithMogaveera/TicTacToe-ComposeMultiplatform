package com.example.kmmsample.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shared.auth.setupAuthNavigation
import com.shared.auth.ui.hooks.AuthProtected
import com.shared.compose_foundation.AppColors
import com.shared.compose_foundation.activity.ProvideActivity
import com.shared.compose_foundation.navigation.navigateSafe
import com.shared.tic_tac_toe.setUpTicTacToeNavigation
import com.shared.profile.ui.ProfileComponent

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
    }
}
