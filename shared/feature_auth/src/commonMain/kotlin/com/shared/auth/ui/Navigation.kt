package com.shared.auth.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.shared.auth.ui.screens.LoginScreen
import com.shared.auth.ui.screens.RegistrationScreen
import com.shared.compose_foundation.navigation.navigateSafe

fun NavGraphBuilder.setupAuthNavigation(navController: NavController) {
    composable("/auth/login") {
        LoginScreen(
            onRegistrationBtnClicked = {
                navController.navigateSafe("/registration")
            },
            onLoginSuccessful = {
                val popBackStack = navController.navigateUp()
                println("Hello $popBackStack")
            }
        )
    }
    composable("/auth/registration") {
        RegistrationScreen(onLoginBtnClick = {
            navController.navigateUp()
        }, onRegistrationSuccessFul = {
            navController.navigateUp()
        })
    }
}


