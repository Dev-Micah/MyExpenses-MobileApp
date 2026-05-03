package com.micahnyabuto.myexpenses

import android.app.Application
import com.micahnyabuto.data.di.dataModule
import com.micahnyabuto.domain.di.domainModule
import com.micahnyabuto.myexpenses.di.presentationModule
import com.micahnyabuto.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyExpensesApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val modules = listOf(
            networkModule,
            domainModule,
            dataModule,
            presentationModule
        )

        startKoin {
            androidLogger()
            androidContext(this@MyExpensesApp)
            modules(modules)
        }
    }

}