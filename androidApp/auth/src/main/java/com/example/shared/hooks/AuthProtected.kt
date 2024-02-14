package com.example.shared.hooks

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.abhijith.auth.viewmodel.usecases.UseCaseAccountActivityMonitor
import com.abhijith.auth.viewmodel.AndroidViewModelAuth
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthProtected(
    vm: AndroidViewModelAuth = koinViewModel(),
    ifNotLogin:()->Unit = {},
    protected: Boolean = true,
    loading: @Composable () -> Unit = { CircularProgressIndicator() },
    content: @Composable () -> Unit = {}
) {
    if (protected) {
        LaunchedEffect(key1 = Unit, block = {
            vm.getLoginState().collectLatest { state ->
                when (state) {
                    is UseCaseAccountActivityMonitor.Response.LoggedInUser -> {

                    }
                    UseCaseAccountActivityMonitor.Response.NoLogin -> {
                        ifNotLogin()
                    }
                }
            }
        })
        when (vm.getLoginState().collectAsState(initial = null).value) {
            is UseCaseAccountActivityMonitor.Response.LoggedInUser -> {content()}
            null -> {loading()}
            UseCaseAccountActivityMonitor.Response.NoLogin -> {}
        }
    } else content()
}

