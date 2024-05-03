package com.shared.compose_foundation.file

import java.io.FileInputStream

actual typealias File = java.io.File

actual fun File.readBytes(): ByteArray  = FileInputStream(this).readBytes()


actual val File.fileName:String get() =  name