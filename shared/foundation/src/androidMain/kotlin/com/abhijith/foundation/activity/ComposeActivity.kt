package com.abhijith.foundation.activity

import androidx.activity.ComponentActivity
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

val LocalActivity = staticCompositionLocalOf<ComponentActivity> {
    noLocalProvidedFor("LocalActivity")
}

private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}


@Composable
fun ComponentActivity.ProvideActivity(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalActivity provides this) {
        content()
    }
}