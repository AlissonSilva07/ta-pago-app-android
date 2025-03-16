package br.alisson.edu.tapago.data.remote.model.auth

data class SignUpResponse(
    val message: String,
    val userLoginResponse: UserLoginResponse
)