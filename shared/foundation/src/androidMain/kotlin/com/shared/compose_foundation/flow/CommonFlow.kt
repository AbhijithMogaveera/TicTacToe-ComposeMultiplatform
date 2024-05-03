package com.shared.compose_foundation.flow

import kotlinx.coroutines.flow.Flow

actual class  CommonFlow<T> actual constructor(
    private val flow: Flow<T>
) : Flow<T> by flow