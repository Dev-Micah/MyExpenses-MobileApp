package com.micahnyabuto.domain.repository

import com.micahnyabuto.domain.helpers.Result
import com.micahnyabuto.domain.models.Expense

import com.micahnyabuto.domain.models.User

interface ExpensesRepository {
    suspend fun getUserData(userId: Long): Result<User>
    suspend fun getAllExpenses(userId: Long): Result<List<Expense>>
    suspend fun createExpense(expense: Expense, userId: Long): Result<Expense>
    suspend fun updateExpense(id: Long, expense: Expense, userId: Long): Result<Expense>
    suspend fun deleteExpense(id: Long): Result<Expense>
}
