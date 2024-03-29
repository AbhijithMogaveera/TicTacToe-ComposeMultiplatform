package com.abhijith.tic_tac_toe.ui.components.invitations

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kmmsample.shared.tic_tac_toe.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import kmmsample.shared.tic_tac_toe.generated.resources.no_content

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