package com.shared.feature_profile.db

import app.cash.sqldelight.db.SqlDriver

expect class DriverFactory constructor(){
    fun createDriver(): SqlDriver
}