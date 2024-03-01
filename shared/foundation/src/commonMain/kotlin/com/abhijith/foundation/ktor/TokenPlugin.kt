package com.abhijith.foundation.ktor

import io.ktor.client.plugins.api.ClientPlugin
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.statement.HttpResponse
interface RequestResponseInterceptor{
    suspend fun onRequest(req:HttpRequestBuilder){}
    suspend fun onResponse(response: HttpResponse){}
    fun onClose(){}
}
private var interceptors:MutableList<RequestResponseInterceptor> = mutableListOf()
object TokenInterceptorPlugin :ClientPlugin<Unit> by createClientPlugin("TokenPlugin", body = {
    onRequest { request, _ ->
       interceptors.forEach {
           it.onRequest(request)
       }
    }
    onResponse {httpResponse->
        interceptors.forEach {
            it.onResponse(httpResponse)
        }
    }
    onClose {
        interceptors.forEach {
            it.onClose()
        }
    }
})

fun TokenInterceptorPlugin.addInterceptor(
    requestResponseInterceptor: RequestResponseInterceptor
){
    interceptors.add(requestResponseInterceptor)
}
fun TokenInterceptorPlugin.removeInterceptor(
    requestResponseInterceptor: RequestResponseInterceptor
){
    interceptors.remove(requestResponseInterceptor)
}

