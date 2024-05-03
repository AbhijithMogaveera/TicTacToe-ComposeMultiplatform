package com.shared.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

class AndroidViewModelAuth
constructor(
    sharedAuthViewModel: SharedAuthViewModel
) : ViewModel(), SharedAuthViewModel by sharedAuthViewModel {
    override var coroutineScope: CoroutineScope = viewModelScope
}