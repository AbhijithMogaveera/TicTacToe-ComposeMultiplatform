package com.abhijith.foundation.ktor.logger

infix fun String.logOf(message:String){
    println("$this : $message")
}
infix fun String.logOf(throwable: Throwable){
    println("$this : ${throwable.stackTraceToString()}")
}

infix fun<T> String.logOf(message:T){
    this logOf message.toString()
}