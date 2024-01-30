package com.abhijith.foundation.prefrence

import com.abhijith.foundation.platform.Kontext

expect fun Kontext.putInt(key: String, value: Int)

expect fun Kontext.getInt(key: String, default: Int): Int

expect fun Kontext.putString(key: String, value: String)

expect fun Kontext.getString(key: String) : String?

expect fun Kontext.putBool(key: String, value: Boolean)

expect fun Kontext.getBool(key: String, default: Boolean): Boolean

expect fun Kontext.removeKey(key: String)

