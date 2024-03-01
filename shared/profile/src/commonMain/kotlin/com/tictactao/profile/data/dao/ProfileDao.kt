package com.tictactao.profile.data.dao

import app.cash.sqldelight.coroutines.asFlow
import arrow.core.Either
import arrow.core.Eval.Companion.always
import arrow.core.Eval.Companion.defer
import arrow.core.Eval.Companion.later
import arrow.core.Eval.Companion.now
import arrow.core.Eval.Companion.raise
import arrow.core.None
import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.left
import arrow.core.right
import com.example.DBProfileDetailsQueries
import com.example.TProfileDetails
import com.tictactao.profile.db.DriverFactory
import com.tictactao.profile.domain.models.User
import com.tictactao.profile.domain.models.UserDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProfileDao {

    private val driver = DBProfileDetailsQueries(DriverFactory().createDriver())

    fun getProfileDetailsAsFlow(): Flow<User> {
        return driver
            .getProfileDetails()
            .asFlow()
            .map {
                it.executeAsList().firstOrNull()
            }.filterNotNull().map {
                User(it.user_name, it.bio, it.profilePicture)
            }
    }

    private fun getProfileDetails(): Either<Throwable, User> =
        driver.getProfileDetails().executeAsOneOrNull()?.let {
            User(it.user_name, it.bio, it.profilePicture).right()
        }?:NoSuchElementException().left()

    suspend fun save(
        userName: Option<String>,
        bio: Option<String>,
        profileImage: Option<String?> = None
    ): Either<Throwable, Unit> = Either.catch {
        withContext(Dispatchers.IO) {
            getProfileDetails()
                .onRight { lastRecord ->
                    driver.saveProfileDetails(
                        user_name = userName.getOrElse { lastRecord.userName },
                        bio = bio.getOrElse { lastRecord.bio },
                        profilePicture = profileImage.getOrElse { lastRecord.profilePicture }
                    )
                }.onLeft {
                    driver.saveProfileDetails(
                        user_name = userName.getOrElse {
                            raise(it).value()
                        },
                        bio = bio.getOrElse {
                            raise(it).value()
                        },
                        profilePicture = profileImage.getOrElse {
                            raise(it).value()
                        }
                    )
                }
        }
    }

    fun clear() {
        driver.clearProfileDetails()
    }
}
