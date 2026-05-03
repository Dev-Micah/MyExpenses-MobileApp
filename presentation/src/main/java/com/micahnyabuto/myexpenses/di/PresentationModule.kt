package com.micahnyabuto.myexpenses.di

import com.micahnyabuto.myexpenses.screens.addexpense.AddExpenseScreenViewModel
import com.micahnyabuto.myexpenses.screens.expenses.ExpensesScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::ExpensesScreenViewModel)
    viewModelOf(::AddExpenseScreenViewModel)
}
