package com.shared.compose_foundation.prefrence

interface KmmPreference {
    fun put(key: String, value: Int)
    fun put(key: String, value: String)
    fun put(key: String, value: Boolean)
    fun getInt(key: String, default: Int): Int
    fun getString(key: String) : String?
    fun getBool(key: String, default: Boolean):Boolean
    fun removeKey(key:String)
}