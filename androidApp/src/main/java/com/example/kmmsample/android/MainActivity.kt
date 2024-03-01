package com.example.kmmsample.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhijith.foundation.activity.ProvideActivity
import com.abhijith.tic_tac_toe.setTicTacToeNavigation
import com.abhijith.auth.setupAuthNavigation
import com.abhijith.auth.ui.hooks.AuthProtected
import com.tictactao.profile.ui.ProfileComponent
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProvideActivity {
                MyApplicationTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = "/profile"
                        ) {
                            setupAuthNavigation(navController)
                            setTicTacToeNavigation(navController)
                            composable("/profile"){
                                AuthProtected(
                                    ifNotLogin = {
                                        navController.navigate("/auth")
                                    }
                                ) {
                                    ProfileComponent(
                                        onLetsPlayClick = {
                                            navController.navigate("/tic_tac_toe")
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
