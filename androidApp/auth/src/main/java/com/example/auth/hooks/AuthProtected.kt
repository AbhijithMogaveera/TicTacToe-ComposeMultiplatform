package com.example.auth.hooks

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.abhijith.foundation.activity.LocalActivity

@Composable
fun AuthProtected(
    ifNotLogin:()->Unit = {},
    protected: Boolean = true,
    loading: @Composable () -> Unit = { CircularProgressIndicator() },
    content: @Composable () -> Unit = {},
) {
    if (protected) {
        val activity = LocalActivity.current
        LaunchedEffect(key1 = Unit, block = {
           /* vm.shared.getLoginState().collectLatest { state ->
                when (state) {
                    is AccountDetailsUseCase.Response.LoggedInUser -> {}
                    AccountDetailsUseCase.Response.NoLogin -> ifNotLogin()
                }
            }*/
        })
       /* when (vm.shared.getLoginState().collectAsState(initial = null).value) {
            null -> {
                loading()
            }
            is AccountDetailsUseCase.Response.LoggedInUser -> content()
            AccountDetailsUseCase.Response.NoLogin -> {}
        }*/
    } else content()
}

