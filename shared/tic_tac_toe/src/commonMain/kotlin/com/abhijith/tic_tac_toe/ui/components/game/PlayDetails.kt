package com.abhijith.tic_tac_toe.ui.components.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import arrow.core.getOrElse
import coil3.compose.AsyncImage
import com.abhijith.tic_tac_toe.domain.models.PlayerProfile

@Composable
fun PlayDetails(
    alignment: PlayerDetailsAlignment,
    modifier: Modifier = Modifier,
    indicatorColor: Color,
    strokeWidth: Dp,
    playerProfile: PlayerProfile,
) {
    CurrentUser().onSome {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.66f)
                    .padding(horizontal = 5.dp)
                    .padding(vertical = 10.dp)
                    .height(80.dp)
                    .align(alignment.let {
                        when (it) {
                            PlayerDetailsAlignment.START -> Alignment.CenterStart
                            PlayerDetailsAlignment.END -> Alignment.CenterEnd
                        }
                    })
                    .clip(CircleShape)
                    .border(BorderStroke(strokeWidth, indicatorColor), CircleShape)
                    .background(color = Color.Black.copy(alpha = 0.6f))
                    .then(modifier)

            ) {
                Row(
                    modifier = Modifier.align(Alignment.CenterStart).fillMaxWidth(),
                    horizontalArrangement = when (alignment) {
                        PlayerDetailsAlignment.START -> {
                            Arrangement.Start
                        }

                        PlayerDetailsAlignment.END -> {
                            Arrangement.End
                        }
                    }
                ) {
                    Spacer(modifier = Modifier.padding(10.dp))
                    if (alignment == PlayerDetailsAlignment.START) {
                        PlayerPic(playerProfile)
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                    Column(
                        horizontalAlignment = when (alignment) {
                            PlayerDetailsAlignment.START -> Alignment.Start
                            PlayerDetailsAlignment.END -> Alignment.End
                        }
                    ) {
                        Text(
                            if (it.userName == playerProfile.user_name) "You" else playerProfile.user_name,
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            playerProfile.bio.getOrElse { "" },
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    if (alignment == PlayerDetailsAlignment.END) {
                        Spacer(modifier = Modifier.padding(10.dp))
                        PlayerPic(playerProfile)
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun PlayerPic(playerProfile: PlayerProfile) {
    Box(modifier = Modifier.size(54.dp).clip(CircleShape).background(Color.Black)) {
        AsyncImage(
            model = playerProfile.profile_image.getOrNull(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            playerProfile.tile.name,
            modifier = Modifier.align(Alignment.BottomEnd).padding(10.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}