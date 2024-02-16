package com.example.kmmsample.android

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.abhijith.foundation.activity.ProvideActivity
import com.abhijith.tic_tac_toe.TicTacToeComponent
import com.example.shared.setupAuthNavigation
import com.example.forum.setUpForumNavigation

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
                        TicTacToeComponent()
                        /*val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = "/forum"
                        ) {
                            setupAuthNavigation(navController)
                            setUpForumNavigation(navController)
                        }*/
                    }
                }
            }
        }
    }
}