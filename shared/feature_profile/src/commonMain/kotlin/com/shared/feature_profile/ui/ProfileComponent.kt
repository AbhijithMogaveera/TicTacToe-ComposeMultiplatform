package com.shared.feature_profile.ui

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.shared.compose_foundation.AppTheme
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.darkrockstudios.libraries.mpfilepicker.MPFile
import com.shared.feature_profile.domain.ProfileViewModel
import com.shared.feature_profile.domain.models.User
import com.shared.feature_profile.domain.use_case.UseCaseUpdateProfileDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first

interface ProfileHandler {
    val currentComponent: StateFlow<ProfileComponentSection>
    val profileUpdateResult: Flow<UseCaseUpdateProfileDetails.UpdateState>
    fun updateComponent(profileComponentSection: ProfileComponentSection)
    fun updateBio(bio: String)
    fun getProfileDetails(): Flow<User>
    fun uploadProfileImage(file: MPFile<Any>)
    fun logout()
}

enum class ProfileComponentSection {
    MainScreen,
    DialogChooseEditOption,
    PickProfileImage,
    EditBio
}

@Composable
fun ProfileComponent(
    onLetsPlayClick: () -> Unit
) {
    val profileViewModel = viewModel { ProfileViewModel() }
    ProfileEditOption(profileHandler = profileViewModel)
    ProfileDetails(
        onLetsPlayClick = onLetsPlayClick,
        profileHandler = profileViewModel
    )
}

@Composable
private fun ProfileDetails(
    onLetsPlayClick: () -> Unit,
    profileHandler: ProfileHandler
) {
    Box(
        modifier = Modifier.fillMaxSize().background(
            color = AppTheme.Background
        )
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center).fillMaxWidth().drawBehind {
            }
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            ProfileDetails(profileHandler)
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
                    profileHandler.logout()
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
private fun ProfileDetails(
    profileHandler: ProfileHandler
) {
    var user: User? by remember { mutableStateOf(null) }
    LaunchedEffect(
        key1 = Unit
    ) {
        profileHandler
            .getProfileDetails()
            .collectLatest {
                user = it
            }
    }
    user?.let { ProfileDetails(it, profileHandler = profileHandler) }
}

@Composable
private fun ProfileDetails(
    user: User,
    profileHandler: ProfileHandler
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(100.dp)
            .clip(CircleShape.copy(topStart = CornerSize(0.dp), bottomStart = CornerSize(0.dp)))
            .border(
                border = BorderStroke(3.dp, color = AppTheme.Container),
                shape = CircleShape.copy(
                    topStart = CornerSize(0.dp),
                    bottomStart = CornerSize(0.dp)
                )
            )
            .background(color = AppTheme.Container),
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
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {
            Text(
                user.userName,
                color = Color.White,
                fontSize = 25.sp,
                modifier = Modifier
            )
            Text(
                user.bio,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 20.sp,
                modifier = Modifier,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        val profileUpdateResult by profileHandler.profileUpdateResult.collectAsState(initial = UseCaseUpdateProfileDetails.UpdateState.Ideal)
        when (profileUpdateResult) {
            UseCaseUpdateProfileDetails.UpdateState.Ideal,
            UseCaseUpdateProfileDetails.UpdateState.Failed,
            UseCaseUpdateProfileDetails.UpdateState.Success -> {
                IconButton(
                    onClick = {
                        profileHandler.updateComponent(ProfileComponentSection.DialogChooseEditOption)
                    },
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
        Spacer(Modifier.width(10.dp))
    }
}

@Composable
private fun ProfileEditOption(
    profileHandler: ProfileHandler
) {
    PickProfileImage(profileHandler)
    Menu(profileHandler)
    EditBio(profileHandler)
}

@Composable
fun EditBio(profileHandler: ProfileHandler) {
    val currentProfileComponent by profileHandler.currentComponent.collectAsState()
    if (currentProfileComponent == ProfileComponentSection.EditBio) {
        var bio by remember {
            mutableStateOf("")
        }
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                Row {
                    TextButton(
                        onClick = {
                            profileHandler.updateComponent(ProfileComponentSection.MainScreen)
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.White),
                    ) {
                        Text(text = "Dismiss")
                    }
                    TextButton(
                        onClick = {
                            profileHandler.updateBio(bio)
                            profileHandler.updateComponent(ProfileComponentSection.MainScreen)
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.White),
                    ) {
                        Text(text = "Update")
                    }
                }
            },
            title = {
                LaunchedEffect(key1 = Unit) {
                    bio = profileHandler.getProfileDetails().first().bio
                }
                OutlinedTextField(
                    value = bio,
                    onValueChange = {
                        bio = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    colors = AppTheme.defaultTextFieldColors,
                    shape = RoundedCornerShape(10.dp),
                    placeholder = {
                        Text(text = "User name")
                    },
                    textStyle = AppTheme.defaultTextFieldTextStyle
                )
            },
            properties = DialogProperties(),
            modifier = Modifier.shadow(
                elevation = 10.dp, shape = RoundedCornerShape(10.dp)
            ),
            containerColor = AppTheme.Container,
            textContentColor = Color.White,
            titleContentColor = Color.Black,
            shape = RoundedCornerShape(10.dp)
        )
    }
}

@Composable
private fun Menu(profileHandler: ProfileHandler) {
    val currentProfileComponent by profileHandler.currentComponent.collectAsState()
    if (currentProfileComponent == ProfileComponentSection.DialogChooseEditOption) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton(
                    onClick = {
                        profileHandler.updateComponent(ProfileComponentSection.MainScreen)
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.White),
                ) {
                    Text(text = "Dismiss")
                }
            },
            title = {
                Column {
                    TextButton(
                        onClick = {
                            profileHandler.updateComponent(ProfileComponentSection.PickProfileImage)
                        }
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            "Choose new profile image",
                            modifier = Modifier,
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    }
                    TextButton(
                        onClick = {
                            profileHandler.updateComponent(ProfileComponentSection.EditBio)
                        }
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Edit Bio", modifier = Modifier, color = Color.White, fontSize = 20.sp)
                    }

                }
            },
            properties = DialogProperties(),
            modifier = Modifier.shadow(
                elevation = 10.dp, shape = RoundedCornerShape(10.dp)
            ),
            containerColor = AppTheme.Background,
            textContentColor = Color.White,
            titleContentColor = Color.Black,
            shape = RoundedCornerShape(10.dp)
        )
    }
}

@Composable
private fun PickProfileImage(profileHandler: ProfileHandler) {
    val currentProfileComponent by profileHandler.currentComponent.collectAsState()
    LaunchedEffect(currentProfileComponent){
        println("HEHEHEHE $currentProfileComponent")
    }
    FilePicker(
        show = currentProfileComponent == ProfileComponentSection.PickProfileImage,
        fileExtensions = listOf("jpg", "png", "webp")
    ) { mpFile ->
        profileHandler.updateComponent(ProfileComponentSection.MainScreen)
        if (mpFile != null) {
            profileHandler.uploadProfileImage(mpFile)
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
                .background(color = AppTheme.Container, shape = CircleShape)
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

