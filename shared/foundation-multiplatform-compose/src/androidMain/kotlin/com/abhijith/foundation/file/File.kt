package com.abhijith.foundation.file

import android.net.Uri
import com.abhijith.foundation.platform.KMMContextProvider
import com.darkrockstudios.libraries.mpfilepicker.MPFile

actual suspend fun MPFile<Any>.getBytes(): ByteArray {
    return KMMContextProvider.getKontext().contentResolver.openInputStream(this.platformFile as Uri)
        .use { stream -> stream!!.readBytes() }
}