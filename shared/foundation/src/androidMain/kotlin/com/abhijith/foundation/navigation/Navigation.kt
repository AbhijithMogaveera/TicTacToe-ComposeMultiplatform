package com.abhijith.foundation.navigation

import androidx.navigation.NavController

fun NavController.navigateSafe(
    route: String
) {
    if(currentDestination?.route != route) {
        navigate(route)
    }
}