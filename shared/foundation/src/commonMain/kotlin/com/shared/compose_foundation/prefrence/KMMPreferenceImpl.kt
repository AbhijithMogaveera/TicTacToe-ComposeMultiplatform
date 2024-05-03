package com.shared.compose_foundation.prefrence

import com.shared.compose_foundation.kotlin.lazyWith
import com.shared.compose_foundation.platform.Kontext

val Kontext.preference by lazyWith {
    KMMPreferenceImpl(it)
}

class KMMPreferenceImpl(
    private val context: Kontext
) : KmmPreference {
    override fun put(key: String, value: Int) = context.putInt(key, value)
    override fun put(key: String, value: String) = context.putString(key, value)
    override fun put(key: String, value: Boolean) = context.putBool(key, value)
    override fun getInt(key: String, default: Int): Int = context.getInt(key, default)
    override fun getString(key: String): String? = context.getString(key)
    override fun getBool(key: String, default: Boolean): Boolean = context.getBool(key, default)
    override fun removeKey(key: String) = context.removeKey(key)
}

