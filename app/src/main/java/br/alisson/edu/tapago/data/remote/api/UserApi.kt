package br.alisson.edu.tapago.data.remote.api

import br.alisson.edu.tapago.data.remote.dto.user.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserApi {
    @GET("user")
    suspend fun getUser(): Response<UserResponse>
}