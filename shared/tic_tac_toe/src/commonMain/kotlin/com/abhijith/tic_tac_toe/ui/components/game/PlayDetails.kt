package com.abhijith.tic_tac_toe.ui.components.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PlayDetails(
    alignment: PlayerDetailsAlignment,
    modifier: Modifier = Modifier,
    indicatorColor: Color,
    strokeWidth: Dp
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(horizontal = 5.dp)
                .padding(vertical = 10.dp)
                .height(100.dp)
                .align(alignment.let {
                    when (it) {
                        PlayerDetailsAlignment.START -> Alignment.CenterStart
                        PlayerDetailsAlignment.END -> Alignment.CenterEnd
                    }
                })
                .clip(CircleShape)
                .border(BorderStroke(strokeWidth, indicatorColor), CircleShape)
                .then(modifier)

        ) {

        }
    }
}