package com.example.myapp.data.repository

import com.example.myapp.data.settings.AppSettings
import com.example.myapp.domain.model.User
import com.example.myapp.domain.repository.UserRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

class UserRepositoryImpl(
    private val httpClient: HttpClient,
    private val settings: AppSettings,
    private val baseUrl: String
) : UserRepository {

    @Serializable
    private data class CreateUserRequest(
        val id_token: String,
        val provider: String
    )

    override suspend fun createUser(idToken: String, provider: String): Result<User> = runCatching {
        httpClient.post("$baseUrl/api/v1/users") {
            contentType(ContentType.Application.Json)
            setBody(CreateUserRequest(id_token = idToken, provider = provider))
        }.body()
    }

    override suspend fun getMe(): Result<User> = runCatching {
        httpClient.get("$baseUrl/api/v1/users/me") {
            settings.authToken?.let { token ->
                header(HttpHeaders.Authorization, "Bearer $token")
            }
        }.body()
    }
}
