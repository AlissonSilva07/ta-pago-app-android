package br.alisson.edu.tapago.data.remote.model

data class LoginRequest(
    val email: String,
    val password: String
)