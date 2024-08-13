package com.shared.compose_foundation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
/**
 * Safely navigates to a new route if the current destination is not already the target route.
 *
 * This function prevents redundant navigation actions by checking if the
 * current destination matches the target route before initiating navigation.
 *
 * @param route The route to navigate to.
 */
fun NavController.navigateIfNotCurrent(
    route: String
) {
    if(currentDestination?.route != route) {
        navigate(route)
    }
}

/**
 * Composable that closes the application when the back button is pressed.
 *
 * This function is platform-specific and should be implemented accordingly
 * to handle back press and close the app.
 *
 * Use this in your UI where back press should trigger app closure.
 */
@Composable
expect fun CloseAppOnBackPress()