package com.abhijith.tic_tac_toe.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import arrow.core.None
import coil3.compose.AsyncImage
import com.abhijith.tic_tac_toe.domain.models.dto.ParticipantDTO
import com.abhijith.tic_tac_toe.domain.viewmodels.TicTacToeViewModel
import com.abhijith.tic_tac_toe.domain.viewmodels.TicTacToeViewModel.PlayRequestState.*
import kmmsample.shared.tic_tac_toe.generated.resources.Res
import kmmsample.shared.tic_tac_toe.generated.resources.no_content
import kmmsample.shared.tic_tac_toe.generated.resources.not_found
import kmmsample.shared.tic_tac_toe.generated.resources.waiting
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

val borderColor = Color("#DDE6ED".toColorInt())

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun TicTacToeComponent() {
    Column(
        modifier = Modifier.background(color = Color("#27374D".toColorInt()))
    ) {

        PendingRequestBottomSheet()
        if (TicTacToeViewModel.requestState != PlayStarted) {
            if (TicTacToeViewModel.requestState == Ended) {
                GoForNextMatch()
            } else {
                ChoosePlayer()
                PartnerPlayerConnectionStatePopUp()
            }
        } else {
            TTTGame()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PendingRequestBottomSheet() {
    val pendingRequests = TicTacToeViewModel.pendingRequest.collectAsState(emptyList()).value
    var showSheet by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = pendingRequests) {
        if (pendingRequests.isEmpty()) {
            showSheet = false
        }
    }
    AnimatedVisibility(
        visible = pendingRequests.isNotEmpty()
    ) {
        TextButton(
            onClick = {
                showSheet = true
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = null,
                tint = Color.Yellow
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                "${pendingRequests.size} Pending requests for play, tap here..!",
                color = Color.White
            )
        }
    }
    val modalBottomSheetState = rememberModalBottomSheetState(true)
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showSheet = false
            },
            sheetState = modalBottomSheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ) {
            LazyColumn(
                modifier = Modifier.defaultMinSize(minHeight = 400.dp)
            ) {
                items(pendingRequests) { (participant, accept, reject) ->
                    ProfileCard(
                        participant,
                        onClick = {},
                        actions = {
                            TextButton(onClick = accept) {
                                Text("Accept")
                            }
                            TextButton(onClick = reject) {
                                Text("Reject")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }
        }
    }
}

@Composable
internal fun GoForNextMatch() {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {
                TicTacToeViewModel.lookForNextMatch()
            },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text("Go for next match")
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun PartnerPlayerConnectionStatePopUp() {
    val showAlertDialog by remember {
        derivedStateOf {
            TicTacToeViewModel.requestState == Waiting
                    || TicTacToeViewModel.requestState == Declined
        }
    }
    if (showAlertDialog) {
        when(TicTacToeViewModel.requestState){
            Waiting -> {
                QuickDialog(
                    res = Res.drawable.waiting,
                    title = "Waiting..",
                    message = "Waiting for other player to accept your request",
                    actions = {
                        AssistChip(onClick = {
                            TicTacToeViewModel.revokeOnGoingReq()
                        }, label = {
                            Text("Withdraw request..! 🙂", color = Color.White)
                        }, shape = CircleShape)
                    },
                    onDismissRequest = {}
                )
            }
            Declined -> {
                QuickDialog(
                    res = Res.drawable.not_found,
                    title = "Request Rejected",
                    message = "Your play request got rejected",
                    actions = {
                        AssistChip(onClick = {
                            TicTacToeViewModel.lookForNextMatch()
                        }, label = {
                            Text("Dismiss 😢", color = Color.White)
                        }, shape = CircleShape)
                    },
                    onDismissRequest = {}
                )
            }
            else->{}
        }


    }
}


@Composable
internal fun ChoosePlayer(
    players: List<ParticipantDTO> = TicTacToeViewModel.player.collectAsState().value,
    onPlayerSelected: (ParticipantDTO) -> Unit = {},
    onSearchValueChange: (String) -> Unit = {}
) {
    LaunchedEffect(key1 = Unit) {
        TicTacToeViewModel.fetchPlayers(searchKey = None)
    }

    Column {
        Column(
            modifier = Modifier.background(color = Color("#526D82".toColorInt()))
        ) {
            Text(
                text = "Players",
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(15.dp),
                color = Color.White,
                fontSize = 25.sp
            )
            SearchBar(
                value = "",
                onValueChange = onSearchValueChange
            )
            Spacer(Modifier.height(1.dp))
        }
        if (players.isEmpty()) {
            NoPlayerPresents()
        } else {
            LazyColumn {
                items(players) {
                    ProfileCard(
                        it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 0.dp, vertical = 0.dp),
                        actions = {
                            AssistChip(
                                onClick = remember {
                                    {
                                        TicTacToeViewModel.requestToPlayerToPlay(it)
                                    }
                                },
                                label = {
                                    Text("Play")
                                },
                                shape = CircleShape
                            )
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun NoPlayerPresents() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.no_content),
                contentDescription = null,
                modifier = Modifier
            )
            Text("No active here player,\n Visit a bit later", color = Color.White)
        }
    }
}

@Composable
private fun ProfileCard(
    profileDetails: ParticipantDTO,
    onClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(0.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color("#DDE6ED".toColorInt()))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .clip(CircleShape)
                    .border(border = BorderStroke(2.dp, Color.White), shape = CircleShape)
                    .size(45.dp)
                    .align(Alignment.CenterVertically)
                    .background(color = Color.Black)
            ) {
                AsyncImage(
                    model = profileDetails.profile_image,
                    contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Column() {
                Text(
                    text = profileDetails.user_name,
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
                Text(
                    profileDetails.user_name,
                    style = TextStyle(
                        color = Color.Black.copy(alpha = 0.7f),
                        fontSize = 16.sp
                    )
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            actions()
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}

@Composable
fun TTTGame() {
    Box(modifier = Modifier.padding(10.dp).drawBehind {
        drawRoundRect(
            color = Color("#E78895".toColorInt()),
            cornerRadius = CornerRadius(30f, 30f)
        )
    }.aspectRatio(1 / 1.5f).fillMaxWidth()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            content = {
                items(count = 9) {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1 / 1f)
                            .padding(10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = Color("#FFE4C9".toColorInt()))
                    ) {}
                }

            }
        )
    }
}

fun String.toColorInt(): Int {
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

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        shape = CircleShape,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null
            )
        },
        placeholder = {
            Text("Your friend name...")
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = borderColor,
            unfocusedIndicatorColor = borderColor
        )
    )
}

@Composable
@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
fun QuickDialog(
    res: DrawableResource? = null,
    title: String,
    message: String,
    actions: @Composable RowScope.() -> Unit = {},
    onDismissRequest: () -> Unit
) {
    BasicAlertDialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color("#000000".toColorInt())),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(all = 20.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(title, color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp))
                Text(message, color = Color.White.copy(alpha = 0.7f), fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(10.dp))
                res?.let { res ->
                    Image(painterResource(res), contentDescription = null)
                }
                Row(
                    content = actions,
                    modifier = Modifier.align(Alignment.End),
                )
            }

        }
    }
}