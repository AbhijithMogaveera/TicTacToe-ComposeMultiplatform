package com.abhijith.foundation.file

import com.darkrockstudios.libraries.mpfilepicker.MPFile

expect suspend fun MPFile<Any>.getBytes(): ByteArray

