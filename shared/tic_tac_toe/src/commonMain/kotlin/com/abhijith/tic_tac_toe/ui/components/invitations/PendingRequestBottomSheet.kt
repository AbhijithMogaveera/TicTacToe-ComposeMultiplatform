package com.abhijith.tic_tac_toe.ui.components.invitations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abhijith.tic_tac_toe.domain.viewmodels.TicTacToeViewModel

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