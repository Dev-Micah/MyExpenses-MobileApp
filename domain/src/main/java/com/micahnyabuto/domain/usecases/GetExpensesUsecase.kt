package com.micahnyabuto.domain.usecases

import com.micahnyabuto.domain.helpers.Result
import com.micahnyabuto.domain.models.Expense
import com.micahnyabuto.domain.repository.ExpensesRepository

class GetExpensesUseCase(
    private val expensesRepository: ExpensesRepository
) {
    suspend operator fun invoke(userId: Long): Result<List<Expense>> {
        return expensesRepository.getAllExpenses(userId)
    }

}


/*
Read on UseCases
 */