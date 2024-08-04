package com.shared.compose_foundation.prefrence

import com.shared.compose_foundation.platform.Kontext

private fun Kontext.getSpEditor() = getSp().edit()

const val SP_NAME = "kmm_app"

actual fun Kontext.putInt(
    key: String,
    value: Int
) {
    getSpEditor().putInt(key, value).apply()
}

actual fun Kontext.getInt(
    key: String,
    default: Int
): Int {
    return getSp().getInt(key, default)
}

actual fun Kontext.putString(
    key: String,
    value: String
) {
    getSpEditor().putString(key, value).apply()
}

actual fun Kontext.getString(
    key: String
): String? {
    return getSp().getString(key, null)
}

actual fun Kontext.putBool(
    key: String,
    value: Boolean
) {
    getSpEditor().putBoolean(key, value).apply()
}

actual fun Kontext.getBool(
    key: String,
    default: Boolean
): Boolean {
    return getSp().getBoolean(key, default)
}

actual fun Kontext.removeKey(key: String) {
    getSpEditor().remove(key).apply()
}

private fun Kontext.getSp() = getSharedPreferences(SP_NAME, 0)



