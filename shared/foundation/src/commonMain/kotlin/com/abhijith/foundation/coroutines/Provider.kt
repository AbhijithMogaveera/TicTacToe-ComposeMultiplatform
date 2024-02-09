package com.abhijith.foundation.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val CoroutineScopeProvider = module {
    single<CoroutineScope> {
        CoroutineScope(
            context = Dispatchers.Main + SupervisorJob()
        )
    }
}