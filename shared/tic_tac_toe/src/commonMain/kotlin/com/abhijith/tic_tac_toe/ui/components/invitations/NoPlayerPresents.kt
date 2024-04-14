package com.abhijith.tic_tac_toe.ui.components.invitations

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import arrow.core.some
import kmmsample.shared.tic_tac_toe.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.time.Timer

import kmmsample.shared.tic_tac_toe.generated.resources.no_content
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock

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
fun Timer(totalTime: Long) {
    Box(
        modifier = Modifier.padding(10.dp)
    ) {
        var progress by remember { mutableStateOf(0f) }
        LaunchedEffect(Unit) {
            var elapsedTime = 0L
            while (elapsedTime < totalTime) {
                val remainingTime = totalTime - elapsedTime
                progress =  (remainingTime.toFloat() / totalTime.toFloat())
                delay(50)
                elapsedTime += 50
            }
        }
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(10.dp),
            strokeCap = StrokeCap.Round,
            trackColor = Color.White,
            color = Color.Red
        )
    }
}


private fun currentTimeInMilis() = Clock.System.now().toEpochMilliseconds()