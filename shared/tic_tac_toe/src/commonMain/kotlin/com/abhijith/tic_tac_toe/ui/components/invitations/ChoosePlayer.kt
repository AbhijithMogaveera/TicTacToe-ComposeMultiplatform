package com.abhijith.tic_tac_toe.ui.components.invitations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import arrow.core.None
import com.abhijith.tic_tac_toe.data.dto.ParticipantDTO
import com.abhijith.tic_tac_toe.domain.viewmodels.TicTacToeViewModel
import com.abhijith.tic_tac_toe.ui.components.toColorInt

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