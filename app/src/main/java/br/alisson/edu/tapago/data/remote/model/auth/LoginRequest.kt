package br.alisson.edu.tapago.data.remote.model.auth

data class LoginRequest(
    val email: String,
    val password: String
)