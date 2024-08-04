package com.shared.compose_foundation.file

import com.darkrockstudios.libraries.mpfilepicker.MPFile

expect suspend fun MPFile<Any>.getBytes(): ByteArray

