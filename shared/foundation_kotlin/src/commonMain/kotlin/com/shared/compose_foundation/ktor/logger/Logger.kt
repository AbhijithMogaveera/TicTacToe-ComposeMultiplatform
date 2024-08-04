package com.shared.compose_foundation.ktor.logger

infix fun String.asTagAndLog(message:String){
    if(this != "ask_to_play"){
        return
    }
    println("$this : $message")
}
infix fun String.asTagAndLog(throwable: Throwable){
    if(this != "ask_to_play"){
        return
    }
    println("$this : ${throwable.stackTraceToString()}")
}

infix fun<T> String.asTagAndLog(message:T){
    if(this != "ask_to_play"){
        return
    }
    this asTagAndLog message.toString()
}