package com.abhijith.foundation.file

import com.darkrockstudios.libraries.mpfilepicker.MPFile

actual suspend fun MPFile<Any>.getBytes(): ByteArray { return this.getFileByteArray() }

