package br.alisson.edu.tapago.data.remote.dto.auth

data class SignUpResponse(
    val message: String,
    val userLoginResponse: UserLoginResponse
)