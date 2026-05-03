package com.micahnyabuto.network.helpers

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

sealed class ApiResponse <out T>{
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(val message: String) : ApiResponse<Nothing>()
}

suspend inline fun <reified T> safeApiCall(
    apiCall: () -> HttpResponse,
): ApiResponse<T>{
    return try {
        val response = apiCall.invoke()
        if (response.status.isSuccess()) {
            ApiResponse.Success(response.body())
        } else {
            val errorDetails = response.bodyAsText()
            ApiResponse.Error("Server Error ${response.status.value}: $errorDetails")
        }
    } catch (e: Exception){
        ApiResponse.Error(e.message ?: "Unexpected error occurred")
    }
}
