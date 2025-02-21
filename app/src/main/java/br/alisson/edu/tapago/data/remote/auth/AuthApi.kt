package br.alisson.edu.tapago.data.remote.auth

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    suspend fun signIn(
        @Body request: LoginRequest
    ): TokenResponse
}