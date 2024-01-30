package com.example.forum.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.example.forum.components.dimes.postImage

@Composable
@Preview
fun Post() {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .aspectRatio(4 / 5f),
        shape = RoundedCornerShape(30.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = "https://images.pexels.com/photos/3844788/pexels-photo-3844788.jpeg",
                contentDescription = "null",
                modifier = Modifier.postImage(),
                contentScale = ContentScale.Crop
            )
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(200.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black
                ),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, " +
                            "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna " +
                            "aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo " +
                            "dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus " +
                            "est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing" +
                            " elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam" +
                            " erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. " +
                            "Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                    color = Color.White,
                    modifier = Modifier.padding(20.dp),
                    overflow = TextOverflow.Ellipsis
                )
            }
        }


    }
}