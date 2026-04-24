package com.micahnyabuto.network

import com.micahnyabuto.network.dtos.ExpenseRequest
import com.micahnyabuto.network.dtos.ExpenseResponse
import com.micahnyabuto.network.dtos.UserResponse
import com.micahnyabuto.network.helpers.ApiResponse
import com.micahnyabuto.network.helpers.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class MyExpensesApiServiceImpl(
    private val client: HttpClient
): MyExpenseApiService {
    override suspend fun getUserData(userId: Int): ApiResponse<UserResponse> {
        return safeApiCall {
            client.get("$BASE_URL/users?$userId")
        }
    }

    override suspend fun getExpenses(userId: Int): ApiResponse<List<ExpenseResponse>> {
        return safeApiCall {
            client.get("$BASE_URL/expenses/user/$userId")
        }
    }

    override suspend fun createExpense(expenseRequest: ExpenseRequest): ApiResponse<ExpenseResponse> {
        return safeApiCall {
            client.post("$BASE_URL/expenses"){
                contentType(ContentType.Application.Json)
                setBody(expenseRequest)
            }
        }
    }

    override suspend fun updateExpense(
        id: Long,
        expenseRequest: ExpenseRequest
    ): ApiResponse<ExpenseResponse> {
        return safeApiCall {
            client.post("$BASE_URL/expenses/$id"){
                contentType(ContentType.Application.Json)
                setBody(expenseRequest)
            }
        }
    }

    override suspend fun deleteExpense(id: Long): ApiResponse<ExpenseResponse> {
        return safeApiCall {
            client.delete("$BASE_URL/expenses/$id")
        }
    }


    companion object{
        const val BASE_URL ="https://10.0.2.2:8080/api"
    }
}

/*
 GitHub Actions
 */