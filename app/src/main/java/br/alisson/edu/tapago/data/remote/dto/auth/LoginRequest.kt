package br.alisson.edu.tapago.data.remote.dto.auth

data class LoginRequest(
    val email: String,
    val password: String
)