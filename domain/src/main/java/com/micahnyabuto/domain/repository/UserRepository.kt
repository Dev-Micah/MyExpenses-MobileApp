package com.micahnyabuto.domain.repository

import com.micahnyabuto.domain.helpers.Result
import com.micahnyabuto.domain.models.User

interface UserRepository {
    suspend fun getUserDetails(userId: Int): Result<User>
}