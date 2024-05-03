package com.shared.compose_foundation.file

expect class File

expect fun File.readBytes():ByteArray
expect val File.fileName:String
