package com.micahnyabuto.domain.repository

import com.micahnyabuto.domain.helpers.Result
import com.micahnyabuto.domain.models.Expense

interface ExpensesRepository {
    suspend fun getAllExpenses(userid: Int): Result<List<Expense>>
}