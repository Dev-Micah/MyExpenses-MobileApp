package com.micahnyabuto.network

import com.micahnyabuto.network.dtos.ExpenseRequest
import com.micahnyabuto.network.dtos.ExpenseResponse
import com.micahnyabuto.network.dtos.UserResponse
import com.micahnyabuto.network.helpers.ApiResponse

interface MyExpenseApiService {
    suspend fun getUserData(userId: Long) : ApiResponse<UserResponse>

    suspend fun getExpenses(userId: Long): ApiResponse<List<ExpenseResponse>>

    suspend fun createExpense(expenseRequest: ExpenseRequest): ApiResponse<ExpenseResponse>

    suspend fun updateExpense(id:Long, expenseRequest: ExpenseRequest): ApiResponse<ExpenseResponse>

    suspend fun deleteExpense(id: Long): ApiResponse<ExpenseResponse>
}