package com.abhijith.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhijith.auth.ui.screens.LoginScreen
import com.abhijith.auth.ui.screens.RegistrationScreen

fun NavGraphBuilder.setupAuthNavigation(mainNavController: NavController) {
    composable("/auth") {
        Box(modifier = Modifier.fillMaxSize()) {
            val authNavyController = rememberNavController()
            NavHost(navController = authNavyController, startDestination = "/login") {
                composable("/login") {
                    LoginScreen(
                        onRegistrationBtnClicked = {
                            authNavyController.navigate("/registration")
                        },
                        onLoginSuccessful = {
                            mainNavController.navigateUp()
                        }
                    )
                }
                composable("/registration") {
                    RegistrationScreen(onLoginBtnClick = {
                        authNavyController.navigateUp()
                    }, onRegistrationSuccessFul = {
                        mainNavController.navigateUp()
                    })
                }
            }
        }


    }
}


