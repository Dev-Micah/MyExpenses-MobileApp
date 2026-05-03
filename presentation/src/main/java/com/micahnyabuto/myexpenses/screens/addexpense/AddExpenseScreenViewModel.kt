package com.micahnyabuto.myexpenses.screens.addexpense

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micahnyabuto.domain.helpers.Result
import com.micahnyabuto.domain.models.Expense
import com.micahnyabuto.domain.usecases.CreateExpenseUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class AddExpenseScreenViewModel(
    private val createExpenseUseCase: CreateExpenseUseCase,
) : ViewModel() {

    var state by mutableStateOf(AddExpenseState(date = "2026-05-4"))
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AddExpenseEvent) {
        when (event) {
            is AddExpenseEvent.OnTitleChanged -> 
                state = state.copy(title = event.title)
            is AddExpenseEvent.OnAmountChanged -> 
                state = state.copy(amount = event.amount)
            is AddExpenseEvent.OnCategoryChanged ->
                state = state.copy(category = event.category)
            is AddExpenseEvent.OnDateChanged -> 
                state = state.copy(date = event.date)
            AddExpenseEvent.OnSaveClick -> saveExpense()
        }
    }

    private fun saveExpense() {
        if (state.title.isBlank() || state.amount.isBlank() || state.category.isBlank()) {
            viewModelScope.launch {
                _uiEvent.send(UiEvent.ShowError("Please fill all fields"))
            }
            return
        }

        viewModelScope.launch {
            state = state.copy(isLoading = true)

            val expense = Expense(
                title = state.title,
                amount = state.amount.toDoubleOrNull() ?: 0.0,
                category = state.category,
                date = state.date,
            )

            val result = createExpenseUseCase(expense, userId = 1L)

            state = state.copy(isLoading = false)

            when (result) {
                is Result.Success -> {
                    _uiEvent.send(UiEvent.ShowSuccess)
                }
                is Result.Error -> {
                    _uiEvent.send(UiEvent.ShowError(result.message))
                }
            }
        }
    }

    sealed class UiEvent {
        object ShowSuccess : UiEvent()
        object NavigateBack : UiEvent()
        data class ShowError(val message: String) : UiEvent()
    }
}