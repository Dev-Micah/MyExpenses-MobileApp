package com.micahnyabuto.network.helpers

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

sealed class ApiResponse <out T>{
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(val message: String) : ApiResponse<Nothing>()
}

suspend inline fun <reified T> safeApiCall(
    apiCall: () -> HttpResponse,
): ApiResponse<T>{
    return try {
        val data = apiCall.invoke()
        ApiResponse.Success(data.body())
    } catch (e: Exception){
        ApiResponse.Error(e.message ?: "Unexpected error occurred")
    }
}