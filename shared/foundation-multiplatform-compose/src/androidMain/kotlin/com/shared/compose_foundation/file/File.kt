package com.shared.compose_foundation.file

import android.net.Uri
import com.shared.compose_foundation.platform.KMMContextProvider
import com.darkrockstudios.libraries.mpfilepicker.MPFile

actual suspend fun MPFile<Any>.getBytes(): ByteArray {
    return KMMContextProvider.getKontext().contentResolver.openInputStream(this.platformFile as Uri)
        .use { stream -> stream!!.readBytes() }
}