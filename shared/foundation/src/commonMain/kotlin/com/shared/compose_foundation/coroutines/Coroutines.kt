package com.shared.compose_foundation.coroutines

import com.shared.compose_foundation.kotlin.lazyWith
import com.shared.compose_foundation.platform.Kontext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

val Kontext.appScope by lazyWith {
    CoroutineScope(SupervisorJob() + Dispatchers.Main)
}