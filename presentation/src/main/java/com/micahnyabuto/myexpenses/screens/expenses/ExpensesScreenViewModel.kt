package com.micahnyabuto.myexpenses.screens.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micahnyabuto.domain.helpers.Result
import com.micahnyabuto.domain.models.Expense
import com.micahnyabuto.domain.usecases.DeleteExpenseUseCase
import com.micahnyabuto.domain.usecases.GetExpensesUseCase
import com.micahnyabuto.domain.usecases.GetUserDataUseCase
import com.micahnyabuto.domain.usecases.UpdateExpenseUseCase
import com.micahnyabuto.myexpenses.utils.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExpensesScreenViewModel(
    private val getExpensesUseCase: GetExpensesUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
) : ViewModel() {

    private val _expensesScreenUiState =
        MutableStateFlow<ExpensesScreenUiState>(ExpensesScreenUiState.Loading)
    val expensesScreenUiState = _expensesScreenUiState.asStateFlow()

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()

    val filteredExpenses: StateFlow<List<Expense>> = combine(
        _expensesScreenUiState,
        _selectedCategory
    ) { state, category ->
        if (state is ExpensesScreenUiState.Success) {
            if (category == null) {
                state.expenses
            } else {
                state.expenses.filter {
                    it.category.equals(
                        category.name, ignoreCase = true
                    )
                }
            }
        } else {
            emptyList()
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val totalBalance: StateFlow<Double> = _expensesScreenUiState.map { state ->
        if (state is ExpensesScreenUiState.Success) {
            state.expenses.sumOf { it.amount }
        } else {
            0.0
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        0.0
    )

    fun onCategorySelected(category: Category?) {
        if (_selectedCategory.value == category) {
            _selectedCategory.value = null
        } else {
            _selectedCategory.value = category
        }
    }

    init {
        getExpenses()
    }

    fun getExpenses() {
        viewModelScope.launch {
            _expensesScreenUiState.update { ExpensesScreenUiState.Loading }
            when (val response = getExpensesUseCase(USER_ID)) {
                is Result.Error -> {
                    _expensesScreenUiState.update {
                        ExpensesScreenUiState.Error(response.message)
                    }
                }

                is Result.Success -> {
                    _expensesScreenUiState.update {
                        ExpensesScreenUiState.Success(response.data)
                    }
                }
            }
        }
    }

    fun getUserData() {
        viewModelScope.launch {
            when (val response = getUserDataUseCase(USER_ID)) {
                is Result.Error -> {

                }

                is Result.Success -> {

                }
            }
        }
    }

    fun updateExpense(id: Long, expense: Expense) {
        viewModelScope.launch {
            when (val response = updateExpenseUseCase(id, expense,
                USER_ID.toLong())) {
                is Result.Error -> {

                }

                is Result.Success -> {
                    getExpenses()
                }
            }
        }
    }

    fun deleteExpense(id: Long) {
        viewModelScope.launch {
            when (val response = deleteExpenseUseCase(id)) {
                is Result.Error -> {

                }

                is Result.Success -> {
                    getExpenses()
                }
            }
        }
    }

    companion object {
        const val USER_ID = 1
    }
}
