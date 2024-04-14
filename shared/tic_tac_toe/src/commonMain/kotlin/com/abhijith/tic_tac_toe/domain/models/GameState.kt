package com.abhijith.tic_tac_toe.domain.models

enum class GameState {
    NotStarted,
    OnGoing,
    PlayerLostAboutToEndInOneMinute,
    End
}