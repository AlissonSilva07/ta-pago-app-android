package br.alisson.edu.tapago.data.remote.dto.auth

data class LoginResponse(
    val message: String,
    val token: String
)