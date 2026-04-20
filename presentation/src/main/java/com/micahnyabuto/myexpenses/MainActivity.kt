package com.micahnyabuto.myexpenses

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.micahnyabuto.myexpenses.navigation.MyExpenseApp
import com.micahnyabuto.myexpenses.ui.theme.MyExpensesTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyExpensesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MyExpenseApp()
                }
            }
        }
    }
}

