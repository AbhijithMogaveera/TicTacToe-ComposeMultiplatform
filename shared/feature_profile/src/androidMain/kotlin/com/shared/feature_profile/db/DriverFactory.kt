package com.shared.feature_profile.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.shared.compose_foundation.platform.KMMContextProvider
import com.shared.feature_profile.sql.Database

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, KMMContextProvider.getKontext(), "profile.db")
    }
}