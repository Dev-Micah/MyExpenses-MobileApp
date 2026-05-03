package com.micahnyabuto.myexpenses.utils

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.LocalPostOffice
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.TravelExplore


object CategoryRegistry {
    val Food = Category(
        name = "Food",
        icon = Icons.Default.Restaurant,
    )

    val Shelter = Category(
        name = "Shelter",
        icon = Icons.Default.House,
    )

    val Travel = Category(
        name = "Travel",
        icon = Icons.Default.TravelExplore,
    )

    val Other = Category(
        name = "Other",
        icon = Icons.Default.MoreHoriz,
    )
}

/**
 * Time stamp to return formatted date
 * e.g. 1633660800000 -> 7th October 2021
 */

@SuppressLint("SimpleDateFormat")
fun Long.timestampToDate(): String {
    return try {
        val date =java.util.Date(this)
        val formatter = java.text.SimpleDateFormat("d MMMM yyyy")
        formatter.format(date)
    } catch (e: Exception){
        this.toString()
    }
}
/**
 * Time stamp to return formatted date
 * e.g. 1633660800000 -> 7th October 2021 9:00 AM
 */

@SuppressLint("SimpleDateFormat")
fun Long.timestampToDateTime(): String {
    return try {
        val date =java.util.Date(this)
        val formatter = java.text.SimpleDateFormat("MMM dd, yyyy hh:mm a")
        formatter.format(date)
    } catch (e: Exception){
        this.toString()
    }
}