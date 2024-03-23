package com.tictactao.profile.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.abhijith.foundation.koin.rememberInject
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.tictactao.profile.domain.ProfileViewModel
import com.tictactao.profile.domain.models.User
import com.tictactao.profile.domain.use_case.UseCaseUpdateProfileDetails
import kotlinx.coroutines.flow.collectLatest

private val LocalProfileViewModel = staticCompositionLocalOf<ProfileViewModel?> { null }

private enum class ProfileComponentSection {
    MainScreen,
    DialogChooseEditOption,
    PickProfileImage,
    EditBio
}

private var currentProfileComponent by mutableStateOf(ProfileComponentSection.MainScreen)

@Composable
fun ProfileComponent(
    onLetsPlayClick: () -> Unit
) {
    val profileViewModel = rememberInject<ProfileViewModel>()
    CompositionLocalProvider(LocalProfileViewModel provides profileViewModel) {
        ProfileEditOption()
        ProfileDetails(
            onLetsPlayClick = onLetsPlayClick,
            onShowProfileEditOptionRequest = {
                currentProfileComponent = ProfileComponentSection.DialogChooseEditOption
            }
        )
    }

}

@Composable
private fun ProfileDetails(
    onLetsPlayClick: () -> Unit,
    onShowProfileEditOptionRequest: () -> Unit
) {
    val profileViewModel = LocalProfileViewModel.current ?: error("Provide LocalProfileViewModel")
    Box(
        modifier = Modifier.fillMaxSize().background(
            color = Color("#05445E".toColorInt())
        )
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center).fillMaxWidth().drawBehind {
            }
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            ProfileDetails(onShowProfileEditOptionRequest)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                "Tic Tac Toe",
                modifier = Modifier.fillMaxWidth().padding(30.dp),
                textAlign = TextAlign.Center,
                fontSize = 50.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.weight(1f))
            PrimaryCTAButton(
                onCLick = onLetsPlayClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextButton(
                onClick = {
                    profileViewModel.logout()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.9f)
            ) {
                Text("Logout", color = Color.White, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun ProfileDetails(onShowProfileEditOptionRequest: () -> Unit) {
    var user: User? by remember { mutableStateOf(null) }
    val profileViewModel = LocalProfileViewModel.current ?: error("Provide LocalProfileViewModel")
    LaunchedEffect(
        key1 = Unit
    ) {
        profileViewModel
            .getProfileDetails()
            .collectLatest {
                user = it
            }
    }
    user?.let { ProfileDetails(it, onShowProfileEditOptionRequest) }
}

@Composable
private fun ProfileDetails(
    user: User,
    onShowProfileEditOptionRequest: () -> Unit
) {
    val profileViewModel = LocalProfileViewModel.current ?: error("Provide LocalProfileViewModel")
    LaunchedEffect(
        key1 = profileViewModel.profileUpdateResult
    ) {
        when (profileViewModel.profileUpdateResult) {
            UseCaseUpdateProfileDetails.UpdateState.Failed -> {}
            UseCaseUpdateProfileDetails.UpdateState.Success -> {}
            else -> {}
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(100.dp)
            .clip(
                CircleShape.copy(
                    topStart = CornerSize(0.dp),
                    bottomStart = CornerSize(0.dp)
                )
            ).border(
                border = BorderStroke(3.dp, color = Color.Black.copy(alpha = 0.9f)),
                shape = CircleShape.copy(
                    topStart = CornerSize(0.dp),
                    bottomStart = CornerSize(0.dp)
                )
            )
            .background(color = Color.Black.copy(alpha = 0.6f)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier.padding(10.dp).clip(CircleShape).border(
                border = BorderStroke(2.dp, Color.White),
                shape = CircleShape
            ).size(70.dp).align(Alignment.CenterVertically).background(color = Color.Black)
        ) {
            AsyncImage(
                model = user.profilePicture,
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            user.userName,
            color = Color.White,
            fontSize = 25.sp,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Spacer(modifier = Modifier.weight(1f))
        when (profileViewModel.profileUpdateResult) {
            UseCaseUpdateProfileDetails.UpdateState.Ideal,
            UseCaseUpdateProfileDetails.UpdateState.Failed,
            UseCaseUpdateProfileDetails.UpdateState.Success -> {
                IconButton(
                    onClick = onShowProfileEditOptionRequest,
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            UseCaseUpdateProfileDetails.UpdateState.InProgress -> {
                CircularProgressIndicator(
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
    }
}

@Composable
private fun ProfileEditOption() {
    PickProfileImage()
    Menu()
}

@Composable
private fun Menu() {
    if (currentProfileComponent == ProfileComponentSection.DialogChooseEditOption) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton(
                    onClick = {
                        currentProfileComponent = ProfileComponentSection.MainScreen
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Red),
                ) {
                    Text(text = "Dismiss")
                }
            },
            title = {
                Column {
                    TextButton(
                        onClick = {
                            currentProfileComponent = ProfileComponentSection.PickProfileImage
                        }
                    ) {
                        Text("Choose new profile image", modifier = Modifier)
                    }
                    TextButton(
                        onClick = {
                            currentProfileComponent = ProfileComponentSection.EditBio
                        }
                    ) {
                        Text("Edit Bio", modifier = Modifier)
                    }

                }
            },
            properties = DialogProperties(),
            modifier = Modifier.shadow(
                elevation = 10.dp, shape = RoundedCornerShape(10.dp)
            )
        )
    }
}

@Composable
private fun PickProfileImage() {
    val profileViewModel = LocalProfileViewModel.current ?: error("Provide ProfileViewModel")
    FilePicker(
        show = currentProfileComponent == ProfileComponentSection.PickProfileImage,
        fileExtensions = listOf("jpg", "png")
    ) { mpFile ->
        currentProfileComponent = ProfileComponentSection.MainScreen
        if (mpFile != null) {
            profileViewModel.uploadProfileImage(mpFile)
        }
    }
}

@Composable
private fun PrimaryCTAButton(
    onCLick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val animatedValue = remember {
        Animatable(0.95f)
    }

    LaunchedEffect(Unit) {
        animatedValue.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 600, easing = EaseInOutQuad),
                repeatMode = RepeatMode.Reverse
            ),
        )
    }

    Box(modifier = modifier.height(70.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(60.dp)
                .align(Alignment.Center)
                .scale(1 * animatedValue.value)
                .border(
                    border = BorderStroke(3.dp, color = Color.White),
                    shape = CircleShape
                )
                .background(color = Color("#189AB4".toColorInt()), shape = CircleShape)
                .clickable {
                    onCLick()
                }
                .padding(10.dp)
        ) {
            Text(
                text = "Lets play >>",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun String.toColorInt(): Int {
    if (this[0] == '#') {
        var color = substring(1).toLong(16)
        if (length == 7) {
            color = color or 0x00000000ff000000L
        } else if (length != 9) {
            throw IllegalArgumentException("Unknown color")
        }
        return color.toInt()
    }
    throw IllegalArgumentException("Unknown color")
}

