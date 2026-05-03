package com.micahnyabuto.myexpenses.screens.addexpense

sealed class AddExpenseEvent {
    data class OnTitleChanged(val title: String) : AddExpenseEvent()
    data class OnAmountChanged(val amount: String) : AddExpenseEvent()
    data class OnCategoryChanged(val category: String) : AddExpenseEvent()
    data class OnDateChanged(val date: String) : AddExpenseEvent()
    object OnSaveClick : AddExpenseEvent()
}