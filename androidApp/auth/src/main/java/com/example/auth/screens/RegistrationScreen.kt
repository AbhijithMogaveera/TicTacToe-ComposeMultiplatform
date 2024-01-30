package com.example.auth.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun RegistrationScreen(
    onLoginBtnClick: () -> Unit = {},
    onRegistrationSuccessFul: () -> Unit
) {
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "Registration"
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
                    value = "",
                    onValueChange = {

                    },
                    placeholder = {
                        Text(text = "User name")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = CircleShape
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {
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
                    onClick = {

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