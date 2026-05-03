package com.micahnyabuto.data.mappers

import com.micahnyabuto.domain.models.Expense
import com.micahnyabuto.domain.models.User
import com.micahnyabuto.network.dtos.ExpenseRequest
import com.micahnyabuto.network.dtos.ExpenseResponse
import com.micahnyabuto.network.dtos.UserResponse


fun ExpenseResponse.toDomain(): Expense{
    return Expense(
        title = title,
        amount = amount,
        category = category,
        date = date,
    )
}

fun Expense.toRequest(userId: Long): ExpenseRequest {
    return ExpenseRequest(
        title = title,
        amount = amount,
        category = category,
        date = date,
        userId = userId
    )
}

fun UserResponse.toDomain(): User {
    return User(
        id = id.toInt(),
        name = name,
        email = email
    )
}
