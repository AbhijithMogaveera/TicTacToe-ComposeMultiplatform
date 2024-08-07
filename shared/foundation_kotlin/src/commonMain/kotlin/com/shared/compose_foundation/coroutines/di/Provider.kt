package com.shared.compose_foundation.coroutines.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val CoroutineScopeProvider = module {
    single<CoroutineScope> {
        CoroutineScope(
            context = Dispatchers.IO + SupervisorJob()
        )
    }
}