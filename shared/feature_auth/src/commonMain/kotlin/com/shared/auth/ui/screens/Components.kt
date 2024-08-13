package com.shared.auth.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shared.compose_foundation.AppTheme
import kmmsample.shared.feature_auth.generated.resources.Res
import kmmsample.shared.feature_auth.generated.resources.password_hide
import kmmsample.shared.feature_auth.generated.resources.password_show
import org.jetbrains.compose.resources.painterResource

object FeatureAuthTheme {
    val ScreenTitleTextStyle = TextStyle(
        color = Color.White,
        fontWeight = FontWeight.SemiBold,
        fontSize = 40.sp,
    )


}

@Composable
fun UserPasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val visualTransformation = remember(passwordVisible) {
        if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
    }
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        colors = AppTheme.defaultTextFieldColors,
        shape = RoundedCornerShape(20.dp),
        placeholder = {
            Text(text = "Password")
        },
        visualTransformation = visualTransformation,
        trailingIcon = {
            PasswordToggleButton(
                passwordVisible,
                onToggle = { passwordVisible = !passwordVisible })
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        textStyle = AppTheme.defaultTextFieldTextStyle
    )
}

@Composable
fun UserNameTextField(userName: String, onUserNameChange: (String) -> Unit) {
    OutlinedTextField(
        value = userName,
        onValueChange = onUserNameChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = AppTheme.defaultTextFieldColors,
        shape = RoundedCornerShape(20.dp),
        placeholder = { Text(text = "Username") },
        textStyle = AppTheme.defaultTextFieldTextStyle
    )
}

@Composable
fun PasswordToggleButton(passwordVisible: Boolean, onToggle: () -> Unit) {
    val pain = if (passwordVisible)
        painterResource(resource = Res.drawable.password_hide)
    else
        painterResource(resource = Res.drawable.password_show)
    val description = if (passwordVisible) "Hide password" else "Show password"
    IconButton(onClick = onToggle) {
        Icon(painter = pain, description)
    }
}
