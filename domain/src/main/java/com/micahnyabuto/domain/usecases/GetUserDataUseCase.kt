package com.micahnyabuto.domain.usecases

import com.micahnyabuto.domain.helpers.Result
import com.micahnyabuto.domain.models.User
import com.micahnyabuto.domain.repository.ExpensesRepository

class GetUserDataUseCase(
    private val expensesRepository: ExpensesRepository
) {
    suspend operator fun invoke(userId: Int): Result<User> {
        return expensesRepository.getUserData(userId)
    }
}
