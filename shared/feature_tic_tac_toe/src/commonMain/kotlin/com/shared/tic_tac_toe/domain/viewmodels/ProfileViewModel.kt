package com.shared.tic_tac_toe.domain.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.None
import arrow.core.Option
import arrow.core.some
import com.shared.feature_profile.domain.models.User
import com.shared.feature_profile.domain.use_case.UseCaseGetProfileDetails
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProfileViewModel : ViewModel(), KoinComponent {
    var loggedInUserDetails: Option<User> by mutableStateOf(None)

    val useCaseGetProfileDetails: UseCaseGetProfileDetails by inject()
    init {
        viewModelScope.launch {
            useCaseGetProfileDetails.getProfileDetails().collectLatest {
                loggedInUserDetails = it.some()
            }
        }
    }
}