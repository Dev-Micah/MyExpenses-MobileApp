package com.micahnyabuto.domain.usecases

import com.micahnyabuto.domain.helpers.Result
import com.micahnyabuto.domain.models.Expense
import com.micahnyabuto.domain.repository.ExpensesRepository

class DeleteExpenseUseCase(
    private val expensesRepository: ExpensesRepository
) {
    suspend operator fun invoke(id: Long): Result<Expense> {
        return expensesRepository.deleteExpense(id)
    }
}
