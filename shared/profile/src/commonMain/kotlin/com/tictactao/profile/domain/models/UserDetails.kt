package com.tictactao.profile.domain.models

sealed class UserDetails {
    data object Loading : UserDetails()
    data object UserNotFound : UserDetails()
    class Found(val user: User) : UserDetails()
}