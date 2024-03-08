package com.tictactao.profile.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.abhijith.auth.viewmodel.usecases.UseCaseLogout
import com.abhijith.foundation.viewmodel.SharedViewModel
import com.darkrockstudios.libraries.mpfilepicker.MPFile
import com.tictactao.profile.domain.models.User
import com.tictactao.profile.domain.use_case.UseCaseGetProfileDetails
import com.tictactao.profile.domain.use_case.UseCaseUpdateProfileDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

internal class ProfileViewModel(
    private val useCaseGetProfileDetails: UseCaseGetProfileDetails,
    private val useCaseUpdateProfileDetails: UseCaseUpdateProfileDetails,
    private val userCaseLogout: UseCaseLogout
) : SharedViewModel, KoinComponent {

    var profileUpdateResult: UseCaseUpdateProfileDetails.UpdateState by mutableStateOf(
        UseCaseUpdateProfileDetails.UpdateState.Ideal
    )

    fun getProfileDetails(): Flow<User> = useCaseGetProfileDetails.getProfileDetails()

    fun uploadProfileImage(it: MPFile<Any>) {
        useCaseUpdateProfileDetails.profileImage(it)
            .onEach {
                profileUpdateResult = it
            }.launchIn(coroutineScope)
    }

    fun logout() {
        coroutineScope.launch {
            userCaseLogout.logout()
        }
    }

    override val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

}