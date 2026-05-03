package com.micahnyabuto.domain.usecases

import com.micahnyabuto.domain.helpers.Result
import com.micahnyabuto.domain.models.Expense
import com.micahnyabuto.domain.repository.ExpensesRepository

class CreateExpenseUseCase(
    private val expensesRepository: ExpensesRepository
) {
    suspend operator fun invoke(expense: Expense, userId: Long): Result<Expense> {
        return expensesRepository.createExpense(expense, userId)
    }
}
