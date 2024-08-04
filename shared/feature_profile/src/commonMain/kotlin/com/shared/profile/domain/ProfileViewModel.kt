package com.shared.profile.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkrockstudios.libraries.mpfilepicker.MPFile
import com.shared.auth.viewmodel.usecases.UseCaseLogout
import com.shared.profile.domain.models.User
import com.shared.profile.domain.use_case.UseCaseGetProfileDetails
import com.shared.profile.domain.use_case.UseCaseUpdateProfileDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class ProfileViewModel() : ViewModel(), KoinComponent {
    private val useCaseGetProfileDetails: UseCaseGetProfileDetails by inject()
    private val useCaseUpdateProfileDetails: UseCaseUpdateProfileDetails by inject()
    private val userCaseLogout: UseCaseLogout by inject()

    var profileUpdateResult: UseCaseUpdateProfileDetails.UpdateState by mutableStateOf(
        UseCaseUpdateProfileDetails.UpdateState.Ideal
    )

    fun getProfileDetails(): Flow<User> = useCaseGetProfileDetails.getProfileDetails()

    fun uploadProfileImage(it: MPFile<Any>) {
        useCaseUpdateProfileDetails.profileImage(it)
            .onEach {
                profileUpdateResult = it
            }.launchIn(viewModelScope)
    }

    fun updateBui(bio: String){
        viewModelScope.launch {
            useCaseUpdateProfileDetails.updateBio(bio)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userCaseLogout.logout()
        }
    }


}