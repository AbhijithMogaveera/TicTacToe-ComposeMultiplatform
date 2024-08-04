package com.shared.compose_foundation.koin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Composable
inline fun<reified T : Any> rememberInject():T {
    return remember {
        object : KoinComponent {
            val t:T by inject<T>()
        }.t
    }
}