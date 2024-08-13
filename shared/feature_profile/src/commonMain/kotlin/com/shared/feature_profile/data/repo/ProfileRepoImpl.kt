package com.shared.feature_profile.data.repo

import arrow.core.Either
import arrow.core.Eval.Companion.raise
import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.some
import com.shared.compose_foundation.arrow.apiCallScope
import com.shared.compose_foundation.file.getBytes
import com.shared.compose_foundation.ktor.ensureSuccessfulRequest
import com.shared.compose_foundation.ktor.exceptions.RequestFailure
import com.shared.compose_foundation.ktor.client.httpClient
import com.darkrockstudios.libraries.mpfilepicker.MPFile
import com.shared.compose_foundation.platform.getHostAddress
import com.shared.feature_profile.data.dao.ProfileDao
import com.shared.feature_profile.data.models.ProfileDetailsResponse
import com.shared.feature_profile.domain.models.ProfileUpdateState
import com.shared.feature_profile.domain.models.User
import com.shared.feature_profile.domain.repo.ProfileRepo
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ProfileRepoImpl(
    val profileDao: ProfileDao
) : ProfileRepo {

    private val profileUpdateState: MutableStateFlow<ProfileUpdateState> =
        MutableStateFlow(ProfileUpdateState.Updated)

    override fun getProfileDetails(
    ): Flow<User> {
        return profileDao.getProfileDetailsAsFlow()
    }

    override suspend fun syncProfileDetailsWithServer(): Either<RequestFailure, Unit> = apiCallScope {
        httpClient.get(urlString = "/app/v1/profile") {
        }.apply {
            syncProfileDetailsWithDB(ensureSuccessfulRequest()).getOrElse(::raise)
        }
    }

    override suspend fun updateProfileDetails(
        bio: Option<String>,
        profileImage: Option<MPFile<Any>>
    ): Either<RequestFailure, Unit> = apiCallScope {
        val fileByteArray = profileImage.map {
            it.getBytes()
        }
        val parts = formData {
            bio.onSome {
                append(
                    key = "bio",
                    value = it
                )
            }
            fileByteArray.onSome { fileByteArray ->
                append(
                    key = "profile_image",
                    value = fileByteArray,
                    headers = Headers.build {
                        append(HttpHeaders.ContentType, "image/png")
                        append(HttpHeaders.ContentDisposition, "filename=\"profileImage.png\"")
                    },
                )
            }
        }
        httpClient
            .submitFormWithBinaryData(
                formData = parts,
                url = "/app/v1/profile"
            ) {
                method = HttpMethod.Put
            }
            .ensureSuccessfulRequest()
            .let { httpResponse ->
                syncProfileDetailsWithDB(httpResponse).getOrElse(::raise)
            }
    }

    override fun clearProfileData() {
        profileDao.clear()
    }

    private suspend fun syncProfileDetailsWithDB(httpResponse: HttpResponse):Either<Throwable, Unit> = Either.catch {
        profileUpdateState.emit(ProfileUpdateState.Updating)
        httpResponse.body<ProfileDetailsResponse>().also {
            profileDao.save(
                userName = it.userName.some(),
                bio = it.bio.some(),
                profileImage = it
                    .profilePicture
                    .replace("localhost", getHostAddress())
                    .also { newPath ->
                        println(newPath)
                    }.some()
            ).getOrElse(::raise)
            profileUpdateState.emit(ProfileUpdateState.Updated)
        }
    }

}