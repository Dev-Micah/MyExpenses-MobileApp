package com.micahnyabuto.myexpenses.utils

import androidx.compose.ui.graphics.vector.ImageVector

data class Category(
    val name: String,
    val icon: ImageVector
)

enum class CategoryTypes{
    Food,
    Shelter,
    Travel,
    Other
}