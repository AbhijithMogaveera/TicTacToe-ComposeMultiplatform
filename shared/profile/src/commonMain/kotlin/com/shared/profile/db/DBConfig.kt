package com.shared.profile.db

import app.cash.sqldelight.db.SqlDriver

expect class DriverFactory constructor(){
    fun createDriver(): SqlDriver
}