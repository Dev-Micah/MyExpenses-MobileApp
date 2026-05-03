package com.micahnyabuto.data.di

import com.micahnyabuto.data.repository.MyExpensesRepositoryImpl
import com.micahnyabuto.domain.repository.ExpensesRepository
import org.koin.dsl.module

val dataModule = module{
    single<ExpensesRepository> { MyExpensesRepositoryImpl(get()) }
}