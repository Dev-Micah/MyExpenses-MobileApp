package com.micahnyabuto.domain.usecases

import com.micahnyabuto.domain.helpers.Result
import com.micahnyabuto.domain.models.Expense
import com.micahnyabuto.domain.repository.ExpensesRepository

class UpdateExpenseUseCase(
    private val expensesRepository: ExpensesRepository
) {
    suspend operator fun invoke(id: Long, expense: Expense, userId: Long): Result<Expense> {
        return expensesRepository.updateExpense(id, expense, userId)
    }
}
