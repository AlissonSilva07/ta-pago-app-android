package br.alisson.edu.tapago.data.remote.model.auth

data class LoginResponse(
    val message: String,
    val token: String
)