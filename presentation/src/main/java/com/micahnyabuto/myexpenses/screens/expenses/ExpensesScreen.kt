package com.micahnyabuto.myexpenses.screens.expenses

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.micahnyabuto.myexpenses.navigation.ExpenseDetail
import com.micahnyabuto.myexpenses.navigation.Expenses

@Composable
fun ExpensesScreen(
    onNavigate: (Any) -> Unit
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                onNavigate(ExpenseDetail("1234567890"))
            }
        ) {
            Text("Navigate")
        }
    }
}