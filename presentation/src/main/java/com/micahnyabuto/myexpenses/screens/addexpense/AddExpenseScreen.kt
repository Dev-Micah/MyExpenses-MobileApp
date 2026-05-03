package com.micahnyabuto.myexpenses.screens.addexpense

import android.R.attr.contentDescription
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.micahnyabuto.myexpenses.components.AppButton
import com.micahnyabuto.myexpenses.components.ExpenseTextField
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddExpenseScreen(
    viewModel: AddExpenseScreenViewModel = koinViewModel(),
    onBack: () -> Unit
){

    val state = viewModel.state
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is AddExpenseScreenViewModel.UiEvent.NavigateBack -> onBack()
                is AddExpenseScreenViewModel.UiEvent.ShowSuccess -> {
                    showSuccessDialog = true
                }
                is AddExpenseScreenViewModel.UiEvent.ShowError -> {
                    errorMessage = event.message
                    showErrorDialog = true
                }
            }
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
                onBack()
            },
            title = { Text("Success") },
            text = { Text("Expense added successfully!") },
            confirmButton = {
                TextButton(onClick = {
                    showSuccessDialog = false
                    onBack()
                }) {
                    Text("OK")
                }
            }
        )
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Error") },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    AddExpenseScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreenContent(
    state: AddExpenseState,
    onEvent: (AddExpenseEvent) -> Unit,
    onBack: () -> Unit
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text("Add Expense")},

                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
},
        bottomBar = {
            Box(
                modifier = Modifier.padding(16.dp)
            ){
                AppButton(
                    text = "Save Expense",
                    isLoading = state.isLoading,
                    onClick = { onEvent(AddExpenseEvent.OnSaveClick) }
                )
            }
        }
    ) {padding->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EnterExpenseBox(
                amount = state.amount,
                onAmountChange = {
                    onEvent(AddExpenseEvent.OnAmountChanged(it))
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            AddExpenseForm(
                title = state.title,
                category = state.category,
                date = state.date,
                onEvent = onEvent
            )
        }
    }

}

@Composable
fun EnterExpenseBox(
    amount: String,
    onAmountChange: (String) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Enter Amount",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "Kes",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )
            TextField(
                value = amount,
                onValueChange = onAmountChange,
                placeholder = { Text(
                    text = "0.00",
                    color = Color.LightGray
                ) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions
                    (keyboardType = KeyboardType.Decimal),
                modifier = Modifier.width(180.dp)
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseForm(
    title: String,
    category: String,
    date: String,
    onEvent: (AddExpenseEvent) -> Unit
) {
    val categories = listOf("Food", "Shelter", "Travel", "Other")
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8F9FA),
                shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text("Title", color = Color.Gray, fontSize = 12.sp)
        ExpenseTextField(
            value = title,
            onValueChange = {
                onEvent(AddExpenseEvent.OnTitleChanged(it))
                            },
            placeholder = "e.g. Weekly Groceries",
            leadingIcon = Icons.Default.Edit,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Category", color = Color.Gray, fontSize = 12.sp)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            ExpenseTextField(
                value = category,
                onValueChange = {},
                readOnly = true,
                placeholder = "Select Category",
                leadingIcon = Icons.Default.Category,
                trailingIcon = {
                    ExposedDropdownMenuDefaults
                        .TrailingIcon(expanded = expanded)
                               },
                modifier = Modifier.menuAnchor(),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { selection ->
                    DropdownMenuItem(
                        text = { Text(selection) },
                        onClick = {
                            onEvent(AddExpenseEvent.OnCategoryChanged(selection))
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Date", color = Color.Gray, fontSize = 12.sp)
        ExpenseTextField(
            value = date,
            onValueChange = { /* Trigger DatePicker */ },
            placeholder = "Select Date",
            leadingIcon = Icons.Default.CalendarToday,
            readOnly = true,
        )
    }
}