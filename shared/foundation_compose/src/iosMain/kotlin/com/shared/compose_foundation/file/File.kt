package com.shared.compose_foundation.file

import com.darkrockstudios.libraries.mpfilepicker.MPFile

actual suspend fun MPFile<Any>.getBytes(): ByteArray { return this.getFileByteArray() }

