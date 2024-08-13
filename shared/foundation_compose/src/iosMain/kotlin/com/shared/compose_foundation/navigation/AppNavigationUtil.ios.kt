package com.shared.compose_foundation.navigation

import androidx.compose.runtime.Composable


@Composable
actual fun CloseAppOnBackPress() {
    throw IllegalStateException("Missing Back Button in OS")
}