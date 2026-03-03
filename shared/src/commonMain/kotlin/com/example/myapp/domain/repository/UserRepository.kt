package com.example.myapp.domain.repository

import com.example.myapp.domain.model.User

interface UserRepository {
    suspend fun createUser(idToken: String, provider: String): Result<User>
    suspend fun getMe(): Result<User>
}
