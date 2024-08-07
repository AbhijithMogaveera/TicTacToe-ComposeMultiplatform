package com.shared.auth.ui

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
        val authNavController = rememberNavController()
        NavHost(
            navController = authNavController,
            startDestination = "/auth/login"
        ) {
            composable("/auth/login") {
                LoginScreen(
                    onRegistrationBtnClicked = {
                        authNavController.navigateSafe("/auth/registration")
                    },
                    onLoginSuccessful = {
                        val popBackStack = mainNavController.navigateUp()
                        println("Hello $popBackStack")
                    }
                )
            }
            composable("/auth/registration") {
                RegistrationScreen(
                    onLoginBtnClick = {
                        authNavController.navigateUp()
                    },
                    onRegistrationSuccessFul = {
                        mainNavController.navigateUp()
                    })
            }
        }
    }


}


