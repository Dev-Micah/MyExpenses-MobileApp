package com.micahnyabuto.myexpenses.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.micahnyabuto.myexpenses.screens.expensedetails.ExpenseDetailsScreen
import com.micahnyabuto.myexpenses.screens.addexpense.AddExpenseScreen
import com.micahnyabuto.myexpenses.screens.addexpense.AddExpenseScreenViewModel
import com.micahnyabuto.myexpenses.screens.expenses.ExpensesScreen
import com.micahnyabuto.myexpenses.screens.expenses.ExpensesScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyExpenseApp(){

    val backStack = remember { mutableStateListOf<Any>(Expenses) }
    val viewModel: ExpensesScreenViewModel = koinViewModel()
    val addExpenseViewModel: AddExpenseScreenViewModel =koinViewModel()

    NavDisplay(
        backStack = backStack,
        onBack = {backStack.removeLastOrNull()},
        entryProvider = entryProvider{
            entry<Expenses> { 
                ExpensesScreen(onNavigate = {
                    route -> backStack.add(route)
                }
                )
            }

            entry<AddExpense> {
                AddExpenseScreen(
                    viewModel = addExpenseViewModel,
                    onBack = { backStack.removeLastOrNull() }
                )
            }

            entry<ExpenseDetail> { route ->
                ExpenseDetailsScreen(id = route.id)
            }
        }
    )
}
