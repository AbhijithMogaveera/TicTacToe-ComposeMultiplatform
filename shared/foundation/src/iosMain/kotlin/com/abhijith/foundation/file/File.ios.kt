package com.abhijith.foundation.file

import io.ktor.utils.io.core.toByteArray
import kotlinx.cinterop.BetaInteropApi
import platform.Foundation.NSFileManager
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.create
import platform.Foundation.lastPathComponent
import kotlin.native.concurrent.freeze

actual typealias File = String

@OptIn(FreezingIsDeprecated::class)
actual fun File.readBytes(): ByteArray {
    TODO()
}

actual val File.fileName: String
    get() {
        val fileURL = NSURL.fileURLWithPath(this)
        return fileURL.lastPathComponent ?: throw IllegalStateException("Failed to get file name")
    }
