package com.shared.auth.ui.hooks

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import arrow.core.None
import arrow.core.Some
import com.shared.auth.viewmodel.AndroidViewModelAuth
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthProtected(
    vm: AndroidViewModelAuth = koinViewModel(),
    ifNotLogin: () -> Unit = {},
    protected: Boolean = true,
    loading: @Composable () -> Unit = { CircularProgressIndicator() },
    content: @Composable () -> Unit = {}
) {
    if (protected) {
        LaunchedEffect(
            key1 = Unit,
            block = {
                vm.getLoginState().collectLatest { state ->
                    when (state) {
                        None -> ifNotLogin()
                        is Some -> {}
                    }
                }
            })
        when (vm.getLoginState().collectAsState().value) {
            None -> {}
            is Some -> content()
        }
    } else content()
}

