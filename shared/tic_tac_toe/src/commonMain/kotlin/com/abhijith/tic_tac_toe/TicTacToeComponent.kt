package com.abhijith.tic_tac_toe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TicTacToeComponent() {
    SquareBoard()
}

@Composable
fun SquareBoard() {
    Box(modifier = Modifier.padding(10.dp).drawBehind {
        drawRoundRect(
            color = Color("#E78895".toColorInt()),
            cornerRadius = CornerRadius(30f, 30f)
        )
    }.aspectRatio(1/1.5f).fillMaxWidth()){
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            content = {
                items(count = 9) {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1/1f)
                            .padding(10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = Color("#FFE4C9".toColorInt()))
                    ){}
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