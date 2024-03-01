package com.abhijith.foundation.kotlin

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.coroutines.internal.synchronized
import kotlin.concurrent.Volatile
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <C,M> lazyWith(build: (C) -> M) = LazyWith(build)
@OptIn(InternalCoroutinesApi::class)
class LazyWith<C, M>(
    val build: (C) -> M
) : ReadOnlyProperty<C, M> {

    private val lock = SynchronizedObject()

    @Volatile
    private var INSTANCE: M? = null

    @OptIn(InternalCoroutinesApi::class)
    override fun getValue(thisRef: C, property: KProperty<*>): M {
        return INSTANCE ?: synchronized(lock) {
            if (INSTANCE == null) {
                INSTANCE = build(thisRef)
            }
            INSTANCE!!
        }
    }
}