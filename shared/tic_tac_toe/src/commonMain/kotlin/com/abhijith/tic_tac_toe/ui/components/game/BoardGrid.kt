package com.abhijith.tic_tac_toe.ui.components.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import arrow.core.None
import arrow.core.Option
import arrow.core.some
import com.abhijith.tic_tac_toe.ui.components.toColorInt

@Composable
fun BoardGrid(modifier: Modifier) {
    val tilesCoordinates = remember {
        mutableStateListOf<Option<LayoutCoordinates>>(
            *(0..<9).map { None }.toTypedArray()
        )
    }
    var boardLayoutCoordinates: Option<LayoutCoordinates> by remember { mutableStateOf(None) }
    LazyVerticalGrid(

        columns = GridCells.Fixed(3),

        content = {
            items(count = 9) { index ->
                Tile(
                    boardState = BoardState.NONE,//BoardState.entries.random(),
                    modifier = Modifier.onGloballyPositioned {
                        tilesCoordinates[index] = it.some()
                    }
                )
            }
        },

        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxWidth()
            .drawBehind {
                drawRoundRect(
                    color = Color("#FF407D".toColorInt()),
                    cornerRadius = CornerRadius(60f, 60f)
                )
            }
            .drawBehind {
                (tilesCoordinates
                    .toList()
                    .takeIf { it.all { option -> option.isSome() } }
                    ?.mapNotNull { it.getOrNull()  }
                    ?.some() ?: None).onSome { tilesCoordinates ->
                    val fromCoordinate = tilesCoordinates[0]
                    val toCoordinate = tilesCoordinates[8]
                    boardLayoutCoordinates.onSome { parentLayoutCoordinates ->
                        drawRect(
                            color = Color.LightGray.copy(alpha = 0.7f),
                            size = fromCoordinate.size.toSize()
                        )
                        drawRect(
                            color = Color.Black.copy(alpha = 0.7f),
                            size = tilesCoordinates[4].size.toSize()
                        )
                        drawLine(
                            start = parentLayoutCoordinates.localPositionOf(
                                sourceCoordinates = fromCoordinate,
                                relativeToSource = Offset.Zero
                            ).let {
                                it.copy(
                                    x = it.x + fromCoordinate.size.width / 2f,
                                    y = it.y + fromCoordinate.size.height / 2f
                                )
                            },
                            end = parentLayoutCoordinates.localPositionOf(
                                sourceCoordinates = toCoordinate,
                                relativeToSource = Offset.Zero
                            ),
                            strokeWidth = 10f,
                            color = Color.Black
                        )
                    }
                }
            }.padding(10.dp)
            .then(modifier).onGloballyPositioned {
                boardLayoutCoordinates = it.some()
            }
    )
}

@Composable
fun Tile(
    boardState: BoardState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .then(modifier)
            .aspectRatio(1 / 1f)
            .padding(10.dp)
            .clip(CircleShape)
            .background(
                color = Color("#FFCAD4".toColorInt()).copy(
                    alpha = when (boardState) {
                        BoardState.NONE -> 0.5f
                        BoardState.O, BoardState.X -> 1f

                    }
                )
            )
    ) {
        val x = when (boardState) {
            BoardState.NONE -> ""
            BoardState.O -> "X"
            BoardState.X -> "O"
        }
        Text(
            x,
            modifier = Modifier.align(Alignment.Center),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }
}