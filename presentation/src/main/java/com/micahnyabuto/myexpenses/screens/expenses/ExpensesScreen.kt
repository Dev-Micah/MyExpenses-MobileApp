package com.micahnyabuto.myexpenses.screens.expenses

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.micahnyabuto.myexpenses.components.CategoryBox
import com.micahnyabuto.myexpenses.navigation.AddExpense
import com.micahnyabuto.myexpenses.utils.Category
import com.micahnyabuto.myexpenses.utils.CategoryRegistry
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(
    onNavigate: (Any) -> Unit,
    viewModel: ExpensesScreenViewModel = koinViewModel(),
) {
    val uiState by viewModel.expensesScreenUiState.collectAsStateWithLifecycle()
    val filteredExpenses by viewModel.filteredExpenses.collectAsStateWithLifecycle()
    val totalBalance by viewModel.totalBalance.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("MyExpenses")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigate(AddExpense) },
                shape = RoundedCornerShape(12.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Expense"
                )
            }
        }
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.getExpenses(isRefreshing = true) },
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                TotalBalanceCard(totalBalance)

                Spacer(modifier = Modifier.height(24.dp))

                CategorySelectionRow(
                    selectedCategory = selectedCategory,
                    onCategorySelected = viewModel::onCategorySelected
                )

                Spacer(modifier = Modifier.height(24.dp))

                RecentActivityRow()

                Spacer(modifier = Modifier.height(16.dp))

                AnimatedContent(
                    targetState = uiState,
                    modifier = Modifier.weight(1f),
                    label = "UIStateAnimation"
                ) { state ->
                    when (state) {
                        is ExpensesScreenUiState.Loading -> {
                            ExpensesLoadingScreen()
                        }

                        is ExpensesScreenUiState.Error -> {
                            ExpensesErrorScreen(
                                errorMessage = state.message
                            )
                        }

                        is ExpensesScreenUiState.Success -> {
                            if (filteredExpenses.isEmpty()) {
                                ExpensesEmptyScreen()
                            } else {
                                ExpenseScreenContent()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ExpenseScreenContent(
    modifier: Modifier = Modifier,
    viewModel: ExpensesScreenViewModel = koinViewModel(),
    onExpenseClick: () -> Unit ={}
){
    val filteredExpenses by viewModel
        .filteredExpenses.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        items(filteredExpenses) { expense ->
            ExpensesCard(
                expense = expense,
                onExpenseClick = onExpenseClick
            )
        }
    }
}

@Composable
fun RecentActivityRow(
    modifier: Modifier= Modifier
){
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Recent Activity",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "See All",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable { /* TODO */ }
        )
    }
}
@Composable
fun ExpensesErrorScreen(
    modifier: Modifier = Modifier,
    errorMessage: String
){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            errorMessage,
            color = Color.Red
        )
    }
}

@Composable
fun ExpensesEmptyScreen(
    modifier: Modifier = Modifier,
){
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("No expenses found")
    }
}
@SuppressLint("DefaultLocale")
@Composable
fun TotalBalanceCard(amount: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "TOTAL BALANCE",
                fontSize = 12.sp,
                color = Color.Gray,
                letterSpacing = 1.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "KES ${String.format("%,.0f", amount)}",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
@Composable
fun ExpensesLoadingScreen(
    modifier: Modifier =Modifier
){
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            strokeWidth = 1.5.dp
        )
    }
}

@Composable
fun CategorySelectionRow(
    selectedCategory: Category?,
    onCategorySelected: (Category?) -> Unit
) {
    val categories = listOf(
        CategoryRegistry.Food,
        CategoryRegistry.Travel,
        CategoryRegistry.Shelter,
        CategoryRegistry.Other
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        categories.forEach { category ->
            CategoryBox(
                category = category,
                onClick = { onCategorySelected(category) },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
            )
        }

    }
}
