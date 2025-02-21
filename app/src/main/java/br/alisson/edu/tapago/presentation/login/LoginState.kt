package br.alisson.edu.tapago.presentation.login

data class LoginState(
    val isLoading: Boolean = false,
    val username: String = "",
    val password: String = ""
)