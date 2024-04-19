package com.abhijith.tic_tac_toe.ui.components.invitations

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.abhijith.foundation.AppColors
import com.abhijith.tic_tac_toe.data.dto.ParticipantDTO
import com.abhijith.tic_tac_toe.domain.Participant
import com.abhijith.tic_tac_toe.ui.components.toColorInt

@Composable
fun ProfileCard(
    profileDetails: Participant,
    onClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    modifier: Modifier
) {
    Card(
        modifier = modifier.padding(5.dp),
        shape = RoundedCornerShape(15.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = AppColors.CONTAINER_SMALL)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
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
                        fontSize = 20.sp
                    )
                )
                Text(
                    profileDetails.user_name,
                    style = TextStyle(
                        color = Color.Black.copy(alpha = 0.7f),
                        fontSize = 18.sp
                    )
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            actions()
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}