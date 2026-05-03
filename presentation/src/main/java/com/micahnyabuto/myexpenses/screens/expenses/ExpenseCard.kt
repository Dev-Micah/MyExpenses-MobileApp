package com.micahnyabuto.myexpenses.screens.expenses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.micahnyabuto.domain.models.Expense
import com.micahnyabuto.myexpenses.utils.CategoryRegistry

@Composable
fun ExpensesCard(
    modifier: Modifier = Modifier,
    expense: Expense,
    onExpenseClick: () -> Unit = {},
) {
    val categoryInfo = when (expense.category.lowercase()) {
        "food" -> CategoryRegistry.Food
        "shelter" -> CategoryRegistry.Shelter
        "travel" -> CategoryRegistry.Travel
        else -> CategoryRegistry.Other
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onExpenseClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = when (expense.category.lowercase()) {
                            "food" -> Color(0xFFFFF1E1)
                            "travel" -> Color(0xFFE1F5FE)
                            "shelter" -> Color(0xFFE8F5E9)
                            else -> Color(0xFFF5F5F5)
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = categoryInfo.icon,
                    contentDescription = null,
                    tint = when (expense.category.lowercase()) {
                        "food" -> Color(0xFFE67E22)
                        "travel" -> Color(0xFF2980B9)
                        "shelter" -> Color(0xFF27AE60)
                        else -> Color.Gray
                    },
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = expense.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    text = "${expense.category} • ${expense.date}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Text(
                text = "-KES ${expense.amount}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE74C3C)
            )
        }
    }
}
