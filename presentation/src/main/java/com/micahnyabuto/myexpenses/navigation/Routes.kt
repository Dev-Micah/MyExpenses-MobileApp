package com.micahnyabuto.myexpenses.navigation

import kotlinx.serialization.Serializable

@Serializable
data object Expenses

@Serializable
data object AddExpense
@Serializable
data class ExpenseDetail(val id: String)


