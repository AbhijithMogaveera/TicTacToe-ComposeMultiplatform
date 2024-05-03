package com.shared.compose_foundation.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import org.koin.core.component.KoinComponent

interface SharedViewModel : KoinComponent {
    val coroutineScope: CoroutineScope
    fun dispose() {
        coroutineScope.cancel()
    }
}