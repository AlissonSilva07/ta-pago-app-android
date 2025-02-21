package br.alisson.edu.tapago.data.remote.auth

data class LoginRequest(
    val email: String,
    val password: String
)