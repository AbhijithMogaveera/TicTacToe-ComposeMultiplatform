package com.shared.auth.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shared.auth.ui.screens.LoginScreen
import com.shared.auth.ui.screens.RegistrationScreen
import com.shared.compose_foundation.navigation.CloseAppOnBackPress
import com.shared.compose_foundation.navigation.navigateIfNotCurrent
import com.shared.compose_foundation.platform.Platform

/**
 * Sets up the authentication-related navigation flow within the app's navigation graph.
 * This function defines the routes and screens involved in the authentication process, including
 * login and registration screens.
 *
 * @param mainNavController The primary navigation controller used for handling the overall app navigation.
 *
 * The function creates a nested navigation graph for authentication, where:
 * - The `/auth` route serves as the entry point to the authentication flow.
 * - The `/auth/login` route displays the `LoginScreen`.
 * - The `/auth/registration` route displays the `RegistrationScreen`.
 *
 * Navigation flow:
 * - From the login screen, users can navigate to the registration screen via the "Register" button.
 * - After a successful login, the main navigation controller handles navigation back to the previous screen.
 * - After successful registration, the registration screen navigates back to the main flow.
 *
 * @see NavGraphBuilder
 * @see NavController
 */
fun NavGraphBuilder.setupAuthNavigation(mainNavController: NavController) {
    composable("/auth") {
        val authNavController = rememberNavController()
        NavHost(
            navController = authNavController,
            startDestination = "/auth/login"
        ) {
            composable("/auth/login") {
                if(Platform.isAndroid){
                    CloseAppOnBackPress()
                }
                LoginScreen(
                    onRegistrationBtnClicked = {
                        authNavController.navigateIfNotCurrent("/auth/registration")
                    },
                    onLoginSuccessful = {
                        mainNavController.navigateUp()
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


