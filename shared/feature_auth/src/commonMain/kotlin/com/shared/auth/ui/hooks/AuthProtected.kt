package com.shared.auth.ui.hooks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shared.auth.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collectLatest

/**
 * A Composable that conditionally displays content based on the user's authentication status.
 *
 * This function checks whether the user is logged in, and if the `protected` flag is true,
 * it either shows the protected content or triggers an action if the user is not logged in.
 *
 * @param vm The `AuthViewModel` instance used to check the user's authentication status.
 *           Defaults to a newly created `AuthViewModel`.
 * @param ifNotLogin A lambda function that gets triggered if the user is not logged in.
 *                   Defaults to an empty function.
 * @param protected A flag indicating whether the content is protected. If true, the content
 *                  is only displayed if the user is logged in. Defaults to true.
 * @param content The Composable content to display if the user is authenticated or
 *                if the `protected` flag is false.
 */
@Composable
fun AuthProtected(
    vm: AuthViewModel = viewModel { AuthViewModel() },
    ifNotLogin: () -> Unit = {},
    protected: Boolean = true,
    content: @Composable () -> Unit = {}
) {
    if (protected) {
        LaunchedEffect(
            key1 = Unit,
            block = {
                vm.isUserLoggedIn.collectLatest { hasToken ->
                    if (!hasToken) {
                        ifNotLogin()
                    }
                }
            })
        if (vm.isUserLoggedIn.collectAsState(initial = false).value) {
            content()
        }
    } else content()
}

