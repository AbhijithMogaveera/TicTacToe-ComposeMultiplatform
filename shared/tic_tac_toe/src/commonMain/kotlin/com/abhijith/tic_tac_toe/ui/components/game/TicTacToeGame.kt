package com.abhijith.tic_tac_toe.ui.components.game

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import arrow.core.None
import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.some
import com.abhijith.tic_tac_toe.ui.components.ActivePlayerIndicatorColor
import com.abhijith.tic_tac_toe.ui.components.InActivePlayerIndicatorColor
import com.abhijith.tic_tac_toe.ui.components.activeStrokeWidth
import com.abhijith.tic_tac_toe.ui.components.inActiveStrokeWidth
import com.abhijith.tic_tac_toe.ui.components.toColorInt
import kotlinx.coroutines.delay

enum class BoardState {
    NONE, O, X
}

enum class PlayerDetailsAlignment {
    START, END
}

@Composable
fun TicTacToeGame(modifier: Modifier = Modifier) {
    var selectedPlayer by remember {
        mutableStateOf(BoardState.NONE)
    }
    LaunchedEffect(
        key1 = Unit
    ) {
        while (true) {
            delay(3000)
            selectedPlayer = BoardState.entries.random()
        }
    }

    var parentLayoutCoordinates: Option<LayoutCoordinates> by remember { mutableStateOf(None) }
    var boardLayoutCoordinates: Option<LayoutCoordinates> by remember { mutableStateOf(None) }
    var playerOneLayoutCoordinates: Option<LayoutCoordinates> by remember { mutableStateOf(None) }
    var playerTwoLayoutCoordinates: Option<LayoutCoordinates> by remember { mutableStateOf(None) }

    val xActiveColor by animateColorAsState(targetValue = if (selectedPlayer == BoardState.X) ActivePlayerIndicatorColor else InActivePlayerIndicatorColor)
    val yActiveColor by animateColorAsState(targetValue = if (selectedPlayer == BoardState.O) ActivePlayerIndicatorColor else InActivePlayerIndicatorColor)

    val xStrokeWidth by animateDpAsState(targetValue = if (selectedPlayer == BoardState.X) activeStrokeWidth else inActiveStrokeWidth)
    val yStrokeWidth by animateDpAsState(targetValue = if (selectedPlayer == BoardState.O) activeStrokeWidth else inActiveStrokeWidth)

    Box {
        Column(
            modifier = modifier
                .background(color = Color("#1B3C73".toColorInt()))
                .padding(10.dp)
                .fillMaxSize()
                .drawBehind {
                    val boardLayoutCoordinates = boardLayoutCoordinates
                        .getOrElse { return@drawBehind }
                    val playerOneLayoutCoordinates = playerOneLayoutCoordinates
                        .getOrElse { return@drawBehind }
                    val playerTwoLayoutCoordinates =
                        playerTwoLayoutCoordinates
                            .getOrElse { return@drawBehind }
                    val parentLayoutCoordinates =
                        parentLayoutCoordinates.getOrElse {
                            return@drawBehind
                        }
                    drawLine(
                        start = Offset(
                            x = boardLayoutCoordinates.size.width.toFloat() - 160f,
                            y = boardLayoutCoordinates.size.height.toFloat()
                        ),
                        end = let {
                            parentLayoutCoordinates
                                .localPositionOf(
                                    playerTwoLayoutCoordinates,
                                    Offset.Zero
                                ).let {
                                    it.copy(
                                        x = boardLayoutCoordinates.size.width.toFloat() - 160f,
                                        y = it.y
                                    )
                                }
                        },
                        color = yActiveColor,
                        strokeWidth = yStrokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        start = Offset(
                            x = 160f,
                            y = boardLayoutCoordinates.localToRoot(Offset.Zero).y + boardLayoutCoordinates.size.height.toFloat()
                        ),
                        end = let {
                            parentLayoutCoordinates
                                .localPositionOf(
                                    playerOneLayoutCoordinates,
                                    Offset.Zero
                                ).let {
                                    it.copy(
                                        x = 160f,
                                        y = it.y
                                    )
                                }
                        },
                        color = xActiveColor,
                        strokeWidth = xStrokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                }.onGloballyPositioned {
                    parentLayoutCoordinates = it.some()
                }
        ) {
            Text(
                text = "Tic Tac Toe",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                textAlign = TextAlign.Center
            )
            BoardGrid(
                modifier = Modifier.onGloballyPositioned {
                    boardLayoutCoordinates = it.some()
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            PlayDetails(
                alignment = PlayerDetailsAlignment.START,
                modifier = Modifier.onGloballyPositioned {
                    playerOneLayoutCoordinates = it.some()
                },
                indicatorColor = xActiveColor,
                strokeWidth = xStrokeWidth
            )
            Spacer(modifier = Modifier.height(10.dp))
            PlayDetails(
                alignment = PlayerDetailsAlignment.END,
                modifier = Modifier.onGloballyPositioned {
                    playerTwoLayoutCoordinates = it.some()
                },
                indicatorColor = yActiveColor,
                strokeWidth = yStrokeWidth
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                AssistChip(
                    onClick = {},
                    label = {
                        Text(
                            "Exit Game..🧐",
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    },
                    shape = CircleShape,
                )
            }
        }
    }
}

