package com.example.shared.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.extension.ButtonWithProgressbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.abhijith.auth.viewmodel.AndroidViewModelAuth
import com.abhijith.auth.viewmodel.usecases.UseCaseAccountActivityMonitor
import com.abhijith.auth.viewmodel.usecases.UseCaseLogin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    onRegistrationBtnClicked: () -> Unit = {},
    onLoginSuccessful: () -> Unit,
    viewModel: AndroidViewModelAuth = koinViewModel()
) {
    val scope = rememberCoroutineScope()
    var isLoginIsInProgress by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getLoginState().collectLatest {
            if (it is UseCaseAccountActivityMonitor.Response.LoggedInUser) {
                onLoginSuccessful()
            }
        }
    })

    var userName by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color("#05445E".toColorInt())
            )
    ) {
        Column {
            Box(modifier = Modifier.height(200.dp)) {
                Text(
                    text = "Login",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 40.sp,
                    modifier = Modifier.padding(vertical = 40.dp, horizontal = 20.dp)
                )
            }
            Card(
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color("#189AB4".toColorInt()),
                ),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
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
                val colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    errorContainerColor = Color.White,
                    focusedIndicatorColor = Color.Black,
                    disabledIndicatorColor = Color.Black,
                    errorIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black
                )
                OutlinedTextField(
                    value = userName,
                    onValueChange = {
                        userName = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    colors = colors,
                    shape = RoundedCornerShape(20.dp),
                    placeholder = {
                        Text(text = "User id")
                    },
                )
                OutlinedTextField(value = password,
                    onValueChange = {
                        password = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    colors = colors,
                    shape = RoundedCornerShape(20.dp),
                    placeholder = {
                        Text(text = "Password")
                    })
                TextButton(
                    onClick = onRegistrationBtnClicked,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "New to here..?", color = Color.White)
                }
                ButtonWithProgressbar(
                    onClick = {
                        scope.launch {
                            if (!isLoginIsInProgress) {
                                isLoginIsInProgress = true
                                delay(2000)
                                val it = viewModel.login(
                                    userName = userName,
                                    password = password
                                ).first()
                                val msg = mapToMessage(it)
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                isLoginIsInProgress = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    bgColor = Color.Black.copy(alpha = 0.7f),
                    inProgress = isLoginIsInProgress,
                ) {
                    Text(text = "Login", color = Color.White, modifier = Modifier.padding(10.dp))
                }
            }
        }
    }
}

private fun mapToMessage(it: UseCaseLogin.Result): String {
    val msg = when (it) {
        UseCaseLogin.Result.LoginSuccessful -> "Login successful"
        UseCaseLogin.Result.INVALID_PASSWORD -> "Please enter valid password"
        UseCaseLogin.Result.INVALID_EMAIL_ID -> "Please enter valid email id"
        UseCaseLogin.Result.CLIENT_SIDE_ERROR -> "Oops! something went please check your internet connection and try again"
        UseCaseLogin.Result.SERVER_SIDE_ISSUE -> "Oops! its us.... please try again later"
        UseCaseLogin.Result.UNKNOWN_ERROR -> "Oops! something went wrong"
    }
    return msg
}