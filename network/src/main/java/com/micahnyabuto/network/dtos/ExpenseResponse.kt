package com.micahnyabuto.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExpenseResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String,
    @SerialName("amount")
    val amount: Double,
    @SerialName("category")
    val category: String,
    @SerialName("date")
    val date: String,
    @SerialName("userId")
    val userId: Long
)