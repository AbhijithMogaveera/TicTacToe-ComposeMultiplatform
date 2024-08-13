package com.shared.auth.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.extension.ButtonWithProgressbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.None
import com.shared.auth.models.LoginResult
import com.shared.auth.viewmodel.AuthViewModel
import com.shared.compose_foundation.AppTheme

/**
 * A Composable function that displays the login screen and handles user authentication.
 *
 * This function allows the user to log in and navigate to the appropriate screen upon successful login.
 * It also provides an option to navigate to the registration screen.
 *
 * @param onRegistrationBtnClicked A lambda function that is triggered when the registration button is clicked.
 *                                 Defaults to an empty function.
 * @param onLoginSuccessful A lambda function that is triggered when the login process is successful.
 * @param viewModel The `AuthViewModel` instance that handles the authentication logic.
 *                  Defaults to a newly created `AuthViewModel`.
 */
@Composable
fun LoginScreen(
    onRegistrationBtnClicked: () -> Unit = {},
    onLoginSuccessful: () -> Unit,
    viewModel: AuthViewModel = viewModel { AuthViewModel() }
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var userName by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(viewModel.loginResult) {
        viewModel.loginResult.onSome { result ->
            if (result == LoginResult.LOGIN_SUCCESSFUL) {
                println("Navigating LoginResult.LOGIN_SUCCESSFUL")
                onLoginSuccessful()
            } else {
                val msg = mapToMessage(result)
                snackbarHostState.showSnackbar(message = msg)
                snackbarHostState.currentSnackbarData?.dismiss()
            }
            viewModel.loginResult = None
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.Background)
    ) {
        Column {
            Row(modifier = Modifier.height(150.dp)) {
                Text(
                    text = "Login",
                    modifier = Modifier.padding(vertical = 40.dp, horizontal = 20.dp),
                    style = FeatureAuthTheme.ScreenTitleTextStyle
                )
            }
            Card(
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                colors = CardDefaults.cardColors(containerColor = AppTheme.Container),
                modifier = Modifier.weight(1f).fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .height(7.dp)
                        .width(50.dp)
                        .clip(CircleShape)
                        .background(color = Color.White.copy(alpha = 0.5f))
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(60.dp))
                UserNameTextField(userName, onUserNameChange = { userName = it })
                UserPasswordTextField(password, onPasswordChange = {password = it})
                TextButton(
                    onClick = onRegistrationBtnClicked,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "New to here..?", color = Color.White)
                }
                ButtonWithProgressbar(
                    onClick = {
                        viewModel.login(userName, password)
                        keyboardController?.hide()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    bgColor = Color.Black.copy(alpha = 0.7f),
                    inProgress = viewModel.isLoginIsInProgress,
                ) {
                    Text(text = "Login", color = Color.White, modifier = Modifier.padding(10.dp))
                }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

}

private fun mapToMessage(it: LoginResult): String {
    val msg = when (it) {
        LoginResult.LOGIN_SUCCESSFUL -> "Login successful"
        LoginResult.INVALID_PASSWORD -> "Please enter valid password"
        LoginResult.INVALID_EMAIL_ID -> "Please enter valid email id"
        LoginResult.CLIENT_SIDE_ERROR -> "Oops! something went please check your internet connection and try again"
        LoginResult.SERVER_SIDE_ISSUE -> "Oops! its us.... please try again later"
        LoginResult.UNKNOWN_ERROR -> "Oops! something went wrong"
    }
    return msg
}