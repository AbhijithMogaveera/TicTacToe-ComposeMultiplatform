package com.abhijith.foundation.viewmodel

import com.abhijith.foundation.kotlin.lazyWith
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob

actual abstract class KViewModel

actual val KViewModel.viewModelScope: CoroutineScope by lazyWith {
    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    scope
}

