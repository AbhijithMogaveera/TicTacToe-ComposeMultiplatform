package com.shared.compose_foundation.file

import platform.Foundation.NSURL
import platform.Foundation.lastPathComponent

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
