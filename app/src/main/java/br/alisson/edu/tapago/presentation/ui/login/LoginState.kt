package br.alisson.edu.tapago.presentation.ui.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    var isLoading: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null
)