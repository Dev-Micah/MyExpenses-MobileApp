package com.micahnyabuto.network.di

import com.micahnyabuto.network.MyExpenseApiService
import com.micahnyabuto.network.MyExpensesApiServiceImpl
import com.micahnyabuto.network.getKtorClient
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule = module{
    single<HttpClient> { getKtorClient() }
    single<MyExpenseApiService> { MyExpensesApiServiceImpl(get()) }

}