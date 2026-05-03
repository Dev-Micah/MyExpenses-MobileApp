package com.micahnyabuto.domain.di

import com.micahnyabuto.domain.usecases.CreateExpenseUseCase
import com.micahnyabuto.domain.usecases.DeleteExpenseUseCase
import com.micahnyabuto.domain.usecases.GetExpensesUseCase
import com.micahnyabuto.domain.usecases.GetUserDataUseCase
import com.micahnyabuto.domain.usecases.UpdateExpenseUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module{
    singleOf(::GetExpensesUseCase)
    singleOf(::GetUserDataUseCase)
    singleOf(::CreateExpenseUseCase)
    singleOf(::UpdateExpenseUseCase)
    singleOf(::DeleteExpenseUseCase)
}
