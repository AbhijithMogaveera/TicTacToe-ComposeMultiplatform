package com.example.shared.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abhijith.auth.viewmodel.usecases.UseCaseAccountActivityMonitor
import com.abhijith.auth.viewmodel.AndroidViewModelAuth
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationScreen(
    onLoginBtnClick: () -> Unit = {},
    onRegistrationSuccessFul: () -> Unit,
    androidViewModelAuth: AndroidViewModelAuth = koinViewModel()
) {
    Scaffold(
        topBar = {
            RegistrationTopAppBar()
        }
    ) { paddingValues ->
        LaunchedEffect(key1 = Unit, block = {
            androidViewModelAuth.getLoginState().collect { response ->
                when (response) {
                    is UseCaseAccountActivityMonitor.Response.LoggedInUser -> {
                        onRegistrationSuccessFul()
                    }

                    UseCaseAccountActivityMonitor.Response.NoLogin -> {
                    }
                }
            }
        })
        var userName: String by remember {
            mutableStateOf("")
        }
        var password: String by remember {
            mutableStateOf("")
        }
        Box(
            Modifier
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                OutlinedTextField(
                    value = userName,
                    onValueChange = remember {
                        { value ->
                            userName = value
                        }
                    },
                    placeholder = {
                        Text(text = "User name")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = CircleShape
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = remember {
                        { value ->
                            password = value
                        }
                    },
                    placeholder = {
                        Text(text = "Password")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = CircleShape
                )
                TextButton(
                    onClick = onLoginBtnClick,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Already have an account?")
                }
                Button(
                    onClick = remember {
                        {
                            androidViewModelAuth.register(userName, password)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Register & login")
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun RegistrationTopAppBar() {
    LargeTopAppBar(
        title = {
            Text(
                text = "Registration"
            )
        }
    )
}