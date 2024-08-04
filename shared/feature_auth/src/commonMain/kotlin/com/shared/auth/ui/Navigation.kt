package com.shared.auth.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shared.auth.ui.screens.LoginScreen
import com.shared.auth.ui.screens.RegistrationScreen
import com.shared.compose_foundation.navigation.navigateSafe

fun NavGraphBuilder.setupAuthNavigation(mainNavController: NavController) {
    composable("/auth") {
        Box(modifier = Modifier.fillMaxSize()) {
            val authNavyController = rememberNavController()
            NavHost(navController = authNavyController, startDestination = "/login") {
                composable("/login") {
                    LoginScreen(
                        onRegistrationBtnClicked = {
                            authNavyController.navigateSafe("/registration")
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


