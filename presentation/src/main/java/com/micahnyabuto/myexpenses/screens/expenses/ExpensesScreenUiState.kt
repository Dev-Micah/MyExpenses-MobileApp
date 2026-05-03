package com.micahnyabuto.myexpenses.screens.expenses

import com.micahnyabuto.domain.models.Expense

sealed interface ExpensesScreenUiState {
    data object Loading : ExpensesScreenUiState

    data class Success(val expenses: List<Expense>) : ExpensesScreenUiState

    data class Error(val message: String) : ExpensesScreenUiState
}