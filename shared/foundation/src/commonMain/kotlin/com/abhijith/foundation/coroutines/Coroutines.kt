package com.abhijith.foundation.coroutines

import com.abhijith.foundation.kotlin.lazyWith
import com.abhijith.foundation.platform.Kontext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

val Kontext.appScope by lazyWith {
    CoroutineScope(SupervisorJob() + Dispatchers.Main)
}