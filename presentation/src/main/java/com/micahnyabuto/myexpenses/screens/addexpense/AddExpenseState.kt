package com.micahnyabuto.myexpenses.screens.addexpense

data class AddExpenseState(
    val title: String = "",
    val amount: String = "",
    val category: String = "",
    val date: String = "",
    val isLoading: Boolean = false
)