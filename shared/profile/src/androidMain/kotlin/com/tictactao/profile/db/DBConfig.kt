package com.tictactao.profile.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.abhijith.foundation.platform.KMMContextProvider
import com.example.Database

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, KMMContextProvider.getKontext(), "profile.db")
    }
}