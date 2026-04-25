package com.micahnyabuto.domain.models

data class Expense(
    val id: Int,
    val title: String,
    val amount: Double,
    val category: String,
    val date: String,
)
