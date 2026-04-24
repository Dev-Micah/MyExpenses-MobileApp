package com.micahnyabuto.myexpenses

import android.app.Application
import com.micahnyabuto.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyExpensesApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val modules = listOf(
            networkModule
        )

        startKoin {
            androidLogger()
            androidContext(this@MyExpensesApp)
            modules(modules)
        }
    }

}