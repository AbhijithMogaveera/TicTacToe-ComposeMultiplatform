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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.None
import arrow.core.Some
import com.shared.auth.models.LoginResult
import com.shared.auth.viewmodel.SharedAuthViewModel
import com.shared.compose_foundation.AppColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Resource
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginScreen(
    onRegistrationBtnClicked: () -> Unit = {},
    onLoginSuccessful: () -> Unit,
    viewModel: SharedAuthViewModel = viewModel { SharedAuthViewModel() }
) {
    val scope = rememberCoroutineScope()
    var isLoginIsInProgress by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getLoginState().collectLatest {
            when (it) {
                None -> {}
                is Some -> onLoginSuccessful()
            }
        }
    })

    var userName by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    Scaffold {
        Box(
            modifier = Modifier.padding(it)
                .fillMaxSize()
                .background(
                    color = AppColors.BACKGROUND
                )
        ) {
            Column {
                Box(modifier = Modifier.height(150.dp)) {
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
                    var passwordVisible by rememberSaveable { mutableStateOf(false) }
                    OutlinedTextField(
                        value = password,
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
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            /*val image = if (passwordVisible)
                                painterResource(id = Res.drawable.hide_password)
                            else
                                painterResource(id = R.drawable.show_password)*/
                            val description = if (passwordVisible) "Hide password" else "Show password"
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
//                            Icon(painter = image, description)
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
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
                                    val it: LoginResult = viewModel.login(
                                        userName = userName,
                                        password = password
                                    ).first()
                                    val msg = mapToMessage(it)
//                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
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

}

private fun mapToMessage(it: LoginResult): String {
    val msg = when (it) {
        LoginResult.LoginSuccessful -> "Login successful"
        LoginResult.INVALID_PASSWORD -> "Please enter valid password"
        LoginResult.INVALID_EMAIL_ID -> "Please enter valid email id"
        LoginResult.CLIENT_SIDE_ERROR -> "Oops! something went please check your internet connection and try again"
        LoginResult.SERVER_SIDE_ISSUE -> "Oops! its us.... please try again later"
        LoginResult.UNKNOWN_ERROR -> "Oops! something went wrong"
    }
    return msg
}