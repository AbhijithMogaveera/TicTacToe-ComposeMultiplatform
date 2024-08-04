package com.shared.auth.ui.screens

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.None
import arrow.core.Some
import com.shared.auth.models.RegistrationResult
import com.shared.auth.viewmodel.SharedAuthViewModel
import com.shared.compose_foundation.AppColors
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(
    onLoginBtnClick: () -> Unit = {},
    onRegistrationSuccessFul: () -> Unit,
    androidViewModelAuth: SharedAuthViewModel = viewModel { SharedAuthViewModel() }
) {
    LaunchedEffect(key1 = Unit, block = {
        androidViewModelAuth
            .getLoginState()
            .collect { response ->
                when(response){
                    None -> {}
                    is Some -> onRegistrationSuccessFul()
                    else -> {}
                }
            }
    })

    var userName: String by remember {
        mutableStateOf("")
    }

    var password: String by remember {
        mutableStateOf("")
    }

    var isRegistrationIsInProgress by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppColors.BACKGROUND)
    ) {
        Column {
            Box(modifier = Modifier.height(200.dp)) {
                Text(
                    text = "Registration",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 40.sp,
                    modifier = Modifier.padding(vertical = 40.dp, horizontal = 20.dp)
                )
            }
            Card(
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AppColors.CONTAINER,
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
                    onClick = onLoginBtnClick,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Already have an account?", color = Color.White)
                }
                ButtonWithProgressbar(
                    bgColor = Color.Black.copy(alpha = 0.7f),
                    inProgress = isRegistrationIsInProgress,
                    onClick = remember {
                        {
                            scope.launch {
                                if (!isRegistrationIsInProgress) {
                                    isRegistrationIsInProgress = true
                                    val res =
                                        androidViewModelAuth.register(userName, password).first()
                                    val msg = mapToStringMessage(res)
                                    isRegistrationIsInProgress = false
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    content = {
                        Text(
                            text = "Register & Login",
                            color = Color.White,
                            modifier = Modifier.padding(10.dp)
                        )
                    },
                )
            }
        }
    }
}

private fun mapToStringMessage(res: RegistrationResult): String {
    val msg = when (res) {
        RegistrationResult.SUCCESS -> "Registration successful"
        RegistrationResult.INVALID_EMAIL_ID -> "Please Enter valid email id"
        RegistrationResult.CLIENT_SIDE_ERROR -> "Opps! something went wrong"
        RegistrationResult.SERVER_SIDE_ISSUE -> "Oops! its us please try again later"
        RegistrationResult.UNKNOWN_ERROR -> "Oops! something went wrong please try again later"
        RegistrationResult.USER_ALREADY_EXISTS -> "User with email id already exits, please re validate your user id"
        RegistrationResult.INVALID_PASSWORD -> "Please enter valid password"
    }
    return msg
}