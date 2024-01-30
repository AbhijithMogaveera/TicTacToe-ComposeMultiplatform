package com.example.auth.screens

import android.widget.Toast
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abhijith.foundation.activity.LocalActivity
import com.abhijith.auth.viewmodel.SharedAuthViewModel
import com.abhijith.auth.viewmodel.usecases.AccountDetailsUseCase
import com.example.auth.viewmodel.AuthViewModel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onRegistrationBtnClicked: () -> Unit = {},
    onLoginSuccessful: () -> Unit
) {
    val activity = LocalActivity.current
//    val viewModel = hiltViewModel<AuthViewModel>()
    LaunchedEffect(key1 = Unit, block = {
       /* viewModel.shared.toastChannel.consumeEach {
            Toast.makeText(activity, it ?: let { "Unknown: he he " }, Toast.LENGTH_SHORT).show()
        }*/
    })
    LaunchedEffect(key1 = Unit, block = {
        /*viewModel.shared.getLoginState().collectLatest {
            if (it is AccountDetailsUseCase.Response.LoggedInUser) {
                onLoginSuccessful()
            }
        }*/
    })
    var userName by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "Login"
                    )
                }
            )
        }
    ) {
        Box(
            Modifier
                .padding(it)
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                OutlinedTextField(
                    value = userName,
                    onValueChange = {
                        userName = it
                    },
                    placeholder = {
                        Text(text = "User name")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = CircleShape
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    placeholder = {
                        Text(text = "Password")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = CircleShape
                )
                TextButton(
                    onClick = onRegistrationBtnClicked,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "New to here..?")
                }
                Button(
                    onClick = {
                      /*  viewModel.shared.login(
                            userName = userName,
                            password = password
                        )*/
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Login")
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}