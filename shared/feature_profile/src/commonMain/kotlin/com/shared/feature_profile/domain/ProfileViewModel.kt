package com.shared.feature_profile.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkrockstudios.libraries.mpfilepicker.MPFile
import com.shared.auth.viewmodel.usecases.UseCaseLogout
import com.shared.feature_profile.domain.models.User
import com.shared.feature_profile.domain.use_case.UseCaseGetProfileDetails
import com.shared.feature_profile.domain.use_case.UseCaseUpdateProfileDetails
import com.shared.feature_profile.ui.ProfileComponentSection
import com.shared.feature_profile.ui.ProfileHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class ProfileViewModel : ViewModel(), KoinComponent, ProfileHandler {

    private val useCaseGetProfileDetails: UseCaseGetProfileDetails by inject()
    private val useCaseUpdateProfileDetails: UseCaseUpdateProfileDetails by inject()
    private val userCaseLogout: UseCaseLogout by inject()

    private val profileUpdateState = MutableStateFlow(UseCaseUpdateProfileDetails.UpdateState.Ideal)

    override var profileUpdateResult: Flow<UseCaseUpdateProfileDetails.UpdateState> = profileUpdateState.asStateFlow()

    override fun getProfileDetails(): Flow<User> = useCaseGetProfileDetails.getProfileDetails()

    override fun uploadProfileImage(file: MPFile<Any>) {
        useCaseUpdateProfileDetails.profileImage(file)
            .onEach {
                profileUpdateState.emit( it )
            }.launchIn(viewModelScope)
    }

    override fun updateBio(bio: String) {
        viewModelScope.launch {
            useCaseUpdateProfileDetails.updateBio(bio)
        }
    }

    override fun logout() {
        viewModelScope.launch {
            userCaseLogout.logout()
        }
    }

    private val _currentComponent = MutableStateFlow(ProfileComponentSection.MainScreen)
    override val currentComponent: StateFlow<ProfileComponentSection> =
        _currentComponent.asStateFlow()

    override fun updateComponent(profileComponentSection: ProfileComponentSection) {
        _currentComponent.update { profileComponentSection }
    }


}