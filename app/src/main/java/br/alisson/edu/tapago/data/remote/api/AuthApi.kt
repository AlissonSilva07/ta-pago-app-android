package br.alisson.edu.tapago.data.remote.api

import br.alisson.edu.tapago.data.remote.model.LoginRequest
import br.alisson.edu.tapago.data.remote.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    suspend fun logIn(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}