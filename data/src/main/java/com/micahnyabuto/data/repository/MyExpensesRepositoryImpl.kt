package com.micahnyabuto.data.repository

import com.micahnyabuto.data.mappers.toDomain
import com.micahnyabuto.data.mappers.toRequest
import com.micahnyabuto.domain.helpers.Result
import com.micahnyabuto.domain.models.Expense
import com.micahnyabuto.domain.models.User
import com.micahnyabuto.domain.repository.ExpensesRepository
import com.micahnyabuto.network.MyExpenseApiService
import com.micahnyabuto.network.helpers.ApiResponse

class MyExpensesRepositoryImpl(
    private val myExpenseApiService: MyExpenseApiService
) : ExpensesRepository {

    override suspend fun getUserData(userId: Long): Result<User> {
        return when (val response = myExpenseApiService.getUserData(userId)) {
            is ApiResponse.Error -> Result.Error(response.message)
            is ApiResponse.Success -> Result.Success(response.data.toDomain())
        }
    }

    override suspend fun getAllExpenses(userId: Long): Result<List<Expense>> {
        return when (val response = myExpenseApiService.getExpenses(userId)) {
            is ApiResponse.Error -> Result.Error(response.message)
            is ApiResponse.Success -> Result.Success(
                response.data.map { it.toDomain() }
            )
        }
    }

    override suspend fun createExpense(expense: Expense, userId: Long): Result<Expense> {
        return when (val response = myExpenseApiService.createExpense(expense.toRequest(userId))) {
            is ApiResponse.Error -> Result.Error(response.message)
            is ApiResponse.Success -> Result.Success(response.data.toDomain())
        }
    }

    override suspend fun updateExpense(id: Long, expense: Expense, userId: Long): Result<Expense> {
        return when (val response = myExpenseApiService.updateExpense(id, expense.toRequest(userId))) {
            is ApiResponse.Error -> Result.Error(response.message)
            is ApiResponse.Success -> Result.Success(response.data.toDomain())
        }
    }

    override suspend fun deleteExpense(id: Long): Result<Expense> {
        return when (val response = myExpenseApiService.deleteExpense(id)) {
            is ApiResponse.Error -> Result.Error(response.message)
            is ApiResponse.Success -> Result.Success(response.data.toDomain())
        }
    }
}
