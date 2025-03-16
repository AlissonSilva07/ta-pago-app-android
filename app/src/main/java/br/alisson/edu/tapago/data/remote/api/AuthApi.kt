package br.alisson.edu.tapago.data.remote.api

import br.alisson.edu.tapago.data.remote.model.LoginRequest
import br.alisson.edu.tapago.data.remote.model.LoginResponse
import br.alisson.edu.tapago.data.remote.model.SignUpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthApi {
    @POST("login")
    suspend fun logIn(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @Multipart
    @POST("register")
    suspend fun signUp(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part profilePicture: MultipartBody.Part
    ): Response<SignUpResponse>
}