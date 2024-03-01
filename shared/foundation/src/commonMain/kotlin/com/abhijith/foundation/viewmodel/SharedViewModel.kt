package com.abhijith.foundation.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

interface SharedViewModel {
    val coroutineScope:CoroutineScope
    fun dispose(){
        coroutineScope.cancel()
    }
}