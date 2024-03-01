package com.tictactao.profile.data.repo

import arrow.core.Either
import arrow.core.Eval.Companion.raise
import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.some
import com.abhijith.foundation.arrow.action
import com.abhijith.foundation.file.getBytes
import com.abhijith.foundation.ktor.ensureSuccessfulRequest
import com.abhijith.foundation.ktor.exceptions.RequestFailure
import com.abhijith.foundation.ktor.client.httpClient
import com.darkrockstudios.libraries.mpfilepicker.MPFile
import com.tictactao.profile.data.dao.ProfileDao
import com.tictactao.profile.data.models.ProfileDetailsResponse
import com.tictactao.profile.domain.models.ProfileUpdateState
import com.tictactao.profile.domain.models.User
import com.tictactao.profile.domain.repo.ProfileRepo
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
import org.koin.core.component.KoinComponent

class ProfileRepoImpl(
    val profileDao: ProfileDao
) : ProfileRepo {

    private val profileUpdateState: MutableStateFlow<ProfileUpdateState> =
        MutableStateFlow(ProfileUpdateState.Updated)

    override fun getProfileDetails(
    ): Flow<User> {
        return profileDao.getProfileDetailsAsFlow()
    }

    override suspend fun syncProfileDetailsWithServer(
    ): Either<RequestFailure, Unit> = action {
        httpClient.get(urlString = "/app/v1/profile") {
        }.apply {
            syncProfileDetailsWithDB(ensureSuccessfulRequest()).getOrElse(::raise)
        }
    }

    override suspend fun updateProfileDetails(
        bio: Option<String>,
        profileImage: Option<MPFile<Any>>
    ): Either<RequestFailure, Unit> = action {
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
                userName = it.user_name.some(),
                bio = it.bio.some(),
                profileImage = it
                    .profile_picture
                    .replace("localhost", "10.0.2.2")
                    .also { newPath ->
                        println(newPath)
                    }.some()
            ).getOrElse(::raise)
            profileUpdateState.emit(ProfileUpdateState.Updated)
        }
    }

}